package kg.edu.mathbilim.controller.api;

import kg.edu.mathbilim.config.S3Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StorageTestController {

    private final S3Config s3Config;

    @GetMapping("/storage/info")
    public Map<String, Object> getStorageInfo() {
        return Map.of(
                "storageType", s3Config.getStorageType(),
                "isMinIO", s3Config.isMinIO(),
                "bucketName", s3Config.getBucketName(),
                "endpoint", s3Config.getEndpoint() != null ? s3Config.getEndpoint() : "Amazon S3"
        );
    }
}
