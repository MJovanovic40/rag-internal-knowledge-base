package rs.raf.mjovanovic40.rag_internal_knowledge_base.config.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.raf.mjovanovic40.rag_internal_knowledge_base.config.properties.AppProperties;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
@RequiredArgsConstructor
public class MinIOClientConfig {

    private final AppProperties appProperties;

    @Bean
    public MinioClient getClient() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient client = MinioClient
                .builder()
                .endpoint(appProperties.getMinioUrl())
                .credentials(appProperties.getMinioUsername(), appProperties.getMinioPassword())
                .build();

        boolean bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(appProperties.getMinioBucketName()).build());

        if (!bucketExists) {
            client.makeBucket(MakeBucketArgs.builder().bucket(appProperties.getMinioBucketName()).build());
        }

        return client;
    }
}
