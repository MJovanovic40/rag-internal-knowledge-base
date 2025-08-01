package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.security.AppUserDetails;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.dto.DocumentDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.dto.DocumentUrlDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.FileService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/knowledge-base")
public class KnowledgeBaseController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<Object> upload(@RequestParam("files")MultipartFile[] files, @AuthenticationPrincipal AppUserDetails user) {
        fileService.save(files, user.getUser().getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getDocuments(@AuthenticationPrincipal AppUserDetails user) {
        return ResponseEntity.ok(fileService.getUserFiles(user.getUser().getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentUrlDto> getDocument(@PathVariable String id) {
        return ResponseEntity.ok(fileService.getFile(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDocument(@PathVariable String id) {
        fileService.delete(id);
        return ResponseEntity.ok().build();
    }

}
