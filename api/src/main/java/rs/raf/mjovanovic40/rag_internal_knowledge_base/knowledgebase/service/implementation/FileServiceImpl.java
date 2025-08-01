package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.exception.CustomException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.dto.DocumentDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.dto.DocumentUrlDto;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.exception.DocumentNotFoundException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.model.Document;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.repository.DocumentRepository;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.FileService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.MinioService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.VectorStoreService;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.utils.FileUtils;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.model.User;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.users.service.UserService;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final ModelMapper modelMapper;

    private final DocumentRepository documentRepository;
    private final UserService userService;
    private final MinioService minioService;
    private final VectorStoreService vectorStoreService;

    @Override
    public void save(MultipartFile[] files, String userId) {
        Arrays.stream(files).forEach(file -> processFile(file, userId));
    }

    @Override
    public List<DocumentDto> getUserFiles(String userId) {
        return documentRepository.findByUser_Id(userId)
                .stream()
                .map(element -> modelMapper.map(element, DocumentDto.class))
                .toList();
    }

    @Override
    public void delete(String id) {
        Document document = findById(id);

        String objectName = FileUtils.getObjectName(document.getName(), document.getId());

        minioService.deleteObject(objectName);
        vectorStoreService.removeFromVectorStore(id);
        documentRepository.delete(document);
    }

    @Override
    public DocumentUrlDto getFile(String id) {
        Document document = findById(id);

        String objectName = FileUtils.getObjectName(document.getName(), document.getId());

        return new DocumentUrlDto(minioService.getPresignedObjectUrl(objectName));
    }

    private Document findById(String id) {
        return documentRepository.findById(id).orElseThrow(DocumentNotFoundException::new);
    }

    private void processFile(MultipartFile file, String userId) {
        String fileName = file.getOriginalFilename();
        User user = userService.findUserById(userId);

        Document document = new Document();
        document.setName(fileName);
        document.setUser(user);
        document = save(document);

        String id = document.getId();

        String objectName = FileUtils.getObjectName(fileName, id);

        try {
            minioService.upload(objectName, file.getContentType(), file.getSize(), file.getInputStream());
            vectorStoreService.addToVectorStore(document);
        } catch (Exception e) {
            documentRepository.delete(document);
            log.error(e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    private Document save(Document document) {
        try {
            return  documentRepository.save(document);
        } catch (Exception e) {
            log.error("Error while saving document.");
            throw new CustomException("Error while saving document.");
        }
    }
}
