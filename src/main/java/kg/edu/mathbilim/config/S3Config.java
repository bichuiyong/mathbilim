package kg.edu.mathbilim.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app.storage")
@EnableConfigurationProperties
@Getter
@Setter
@Slf4j
public class S3Config {

    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;
    private String baseUrl;

    private String endpoint;

    private Map<String, String> maxSizes;
    private Map<String, List<String>> allowedTypes;
    private Map<String, Map<String, String>> folders;

    @PostConstruct
    public void logConfiguration() {
        String storageType = isMinIO() ? "MinIO" : "Amazon S3";
        log.info("=== STORAGE CONFIGURATION ===");
        log.info("Storage Type: {}", storageType);
        log.info("Bucket Name: {}", bucketName);
        log.info("Region: {}", region);
        if (isMinIO()) {
            log.info("MinIO Endpoint: {}", endpoint);
        }
        log.info("Base URL: {}", baseUrl);
        log.info("=============================");
    }

    @Bean
    public S3Client s3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        var builder = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials));

        if (isMinIO()) {
            builder.endpointOverride(URI.create(endpoint))
                    .forcePathStyle(true);
            log.info("S3Client configured for MinIO at {}", endpoint);
        } else {
            log.info("S3Client configured for Amazon S3");
        }

        return builder.build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        S3Presigner.Builder builder = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials));

        if (isMinIO()) {
            builder.endpointOverride(URI.create(endpoint));
            log.info("S3Presigner configured for MinIO");
        } else {
            log.info("S3Presigner configured for Amazon S3");
        }

        return builder.build();
    }

    public String getFolder(String context, String type) {
        Map<String, String> contextFolders = folders.get(context);
        return contextFolders != null ? contextFolders.get(type) : folders.get("general").get("uploads");
    }

    public boolean isMinIO() {
        return endpoint != null && !endpoint.isEmpty();
    }

    public String getStorageType() {
        return isMinIO() ? "MinIO" : "Amazon S3";
    }
}