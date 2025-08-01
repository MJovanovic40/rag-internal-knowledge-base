package rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.implementation;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.exception.CustomException;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.properties.AppProperties;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.knowledgebase.service.MinioService;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final AppProperties appProperties;

    @Override
    public void deleteObject(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(appProperties.getMinioBucketName())
                            .object(objectName)
                            .build()
            );
        } catch (Exception e ) {
            log.error(e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public String getPresignedObjectUrl(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs
                            .builder()
                            .bucket(appProperties.getMinioBucketName())
                            .object(objectName)
                            .expiry(1, TimeUnit.DAYS)
                            .method(Method.GET)
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public InputStream getObject(String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs
                            .builder()
                            .bucket(appProperties.getMinioBucketName())
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public void upload(String objectName, String contentType, Long size, InputStream inputStream) {
        try {
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(appProperties.getMinioBucketName())
                            .object(objectName)
                            .contentType(contentType)
                            .stream(inputStream, size, -1)
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
