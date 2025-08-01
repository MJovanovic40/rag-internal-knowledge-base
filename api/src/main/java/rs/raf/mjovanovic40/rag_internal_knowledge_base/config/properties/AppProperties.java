package rs.raf.mjovanovic40.rag_internal_knowledge_base.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppProperties {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.username}")
    private String minioUsername;

    @Value("${minio.password}")
    private String minioPassword;

    @Value("${minio.bucket.name}")
    private String minioBucketName;
}
