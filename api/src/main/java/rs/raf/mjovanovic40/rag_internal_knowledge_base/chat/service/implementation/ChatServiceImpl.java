package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.agent.service.LLMService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.ChatDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.response.ChatChunkResponse;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.exception.ChatNotFoundException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.model.Chat;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.repository.ChatRepository;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.ChatService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.exception.CustomException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.service.UserService;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ModelMapper modelMapper;
    private final ChatRepository chatRepository;
    private final UserService userService;

    @Lazy
    @Autowired
    private LLMService llmService;

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
    public Flux<ServerSentEvent<ChatChunkResponse>> sendMessage(String chatId, String message, String userId) {
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
        return llmService
                .streamLLM(chatId, new UserMessage(message))
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
