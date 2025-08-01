package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service;

import java.io.InputStream;

public interface MinioService {
    void deleteObject(String objectName);
    String getPresignedObjectUrl(String objectName);
    InputStream getObject(String objectName);
    void upload(String objectName, String contentType, Long size, InputStream inputStream);
}
