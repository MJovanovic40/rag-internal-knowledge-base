package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service;

import org.springframework.web.multipart.MultipartFile;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.dto.DocumentDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.dto.DocumentUrlDto;

import java.util.List;

public interface FileService {
    void save(MultipartFile[] files, String userId);
    List<DocumentDto> getUserFiles(String userId);
    void delete(String id);
    DocumentUrlDto getFile(String id);
}
