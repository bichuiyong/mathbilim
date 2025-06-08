package kg.edu.mathbilim.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app.storage")
@EnableConfigurationProperties
@Getter
@Setter
@DependsOn("environmentConfig")
public class S3Config {

    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;
    private String baseUrl;

    private Map<String, String> maxSizes;
    private Map<String, List<String>> allowedTypes;
    private Map<String, Map<String, String>> folders;

    @Bean
    public S3Client s3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    public String getFolder(String context, String type) {
        Map<String, String> contextFolders = folders.get(context);
        return contextFolders != null ? contextFolders.get(type) : folders.get("general").get("uploads");
    }
}