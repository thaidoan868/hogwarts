package vn.conguyetduong.hogwarts.app.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import vn.conguyetduong.hogwarts.infra.client.objectstorage.ObjectStorageClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ObjectStorageClientHealth implements HealthIndicator {
    private final ObjectStorageClient objectStorageClient;

    @Value("${resources.bucket}")
    private String bucket;

    private String objectName = UUID.randomUUID().toString();

    @Override
    public Health health() {

        try {
            byte[] content = "Dummy Content".getBytes(StandardCharsets.UTF_8);
            InputStream is = new ByteArrayInputStream(content);

            objectStorageClient.putObject(bucket, objectName, is, content.length, "text/plain");
            objectStorageClient.statObject(bucket, objectName);

            return Health.up()
                    .withDetail("ObjectStorageClient", objectStorageClient.getClass().getSimpleName())
                    .withDetail("bucket", bucket)
                    .withDetail("operation", "upload/stat")
                    .build();

        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("ObjectStorageClient", objectStorageClient.getClass().getSimpleName())
                    .withDetail("bucket", bucket)
                    .build();
        }
    }
}
