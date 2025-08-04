package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.LLMGraphProvider;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.graph.state.MyAgentState;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service.LLMService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.ChatDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.response.ChatChunkResponse;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.exception.ChatNotFoundException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.Chat;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.repository.ChatRepository;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.ChatService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.exception.CustomException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.service.UserService;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ModelMapper modelMapper;
    private final ChatRepository chatRepository;
    private final UserService userService;
    private final LLMService llmService;

    @Lazy
    @Autowired
    private LLMGraphProvider llmGraphProvider;

    @Override
    public Chat findById(String id) {
        return chatRepository.findById(id).orElseThrow(ChatNotFoundException::new);
    }

    @Override
    public Chat create(String title, String userId) {
        Chat chat = new Chat();
        chat.setTitle(title);
        chat.setUser(userService.findUserById(userId));
        return saveChat(chat);
    }

    @Override
    public void delete(String id) {
        chatRepository.deleteById(id);
    }

    @Override
    public Flux<ServerSentEvent<ChatChunkResponse>> sendMessage(String chatId, String message, String userId, Boolean useRag) {
        String title;
        if (chatId == null) {
            title = llmService.promptLLM(
                    new UserMessage("Generate a title (only consisting of a few words without any reasoning) for the topic of the following prompt: " + message)
            );
            chatId = create(title, userId).getId();
        } else {
            title = null;
        }
        String finalChatId = chatId;

        Optional<MyAgentState> optState = llmGraphProvider.getGraph()
                .invoke(
                        Map.of(
                                MyAgentState.PROMPT_KEY, message,
                                MyAgentState.CONVERSATION_ID_KEY, finalChatId,
                                MyAgentState.USE_RAG_KEY, useRag,
                                MyAgentState.USER_ID_KEY, userId
                        )
                );

        if(optState.isEmpty()) throw new CustomException("Error while executing graph.");

        MyAgentState state = optState.get();

        String responseText = state.response();

        Flux<String> simulatedStream = Flux.fromIterable(responseText.chars()
                        .mapToObj(c -> String.valueOf((char) c))
                        .toList())
                .delayElements(Duration.ofMillis(10));

        return simulatedStream
                .concatWith(Flux.just("END"))
                .map(content -> new ChatChunkResponse(finalChatId, title, content))
                .map(chunk -> ServerSentEvent.builder(chunk).build());
    }

    @Override
    public List<ChatDto> getUserChats(String userId) {
        return chatRepository.findByUser_Id(userId)
                .stream()
                .map(element -> modelMapper.map(element, ChatDto.class))
                .sorted(Comparator.comparing(ChatDto::getCreatedAt))
                .toList()
                .reversed();
    }

    @Override
    public ChatDto findDtoById(String id) {
        return modelMapper.map(findById(id), ChatDto.class);
    }

    private Chat saveChat(Chat chat) {
        try {
            return chatRepository.save(chat);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomException(e.getMessage());
        }
    }
}
