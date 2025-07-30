package rs.raf.mjovanovic40.rag_internal_knowledge_base.chat;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.ChatDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.ChatMessageDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.request.ChatRequest;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.dto.response.ChatChunkResponse;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.ChatMessageService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.chat.service.ChatService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.security.AppUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatMessageService chatMessageService;
    private final ModelMapper modelMapper;

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ChatChunkResponse>> sendMessage(@RequestBody @Validated ChatRequest body, @AuthenticationPrincipal AppUserDetails user) {
        return chatService.sendMessage(body.getChatId(), body.getMessage(), user.getUser().getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDto> getChat(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(chatService.findById(id), ChatDto.class));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ChatDto>> getUserChats(@AuthenticationPrincipal AppUserDetails user) {
        return ResponseEntity.ok(chatService.getUserChats(user.getUser().getId()));
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<List<ChatMessageDto>> getHistory(@PathVariable String id) {
        return ResponseEntity.ok(chatMessageService.getChatHistory(id).stream().map(element -> modelMapper.map(element, ChatMessageDto.class)).toList());
    }
}
