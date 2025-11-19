package vn.conguyetduong.hogwarts.app.health;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.RemoveObjectArgs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MiniIO implements HealthIndicator {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;
    private String objectName = UUID.randomUUID().toString();

    @Override
    public Health health() {

        try {
            // Dummy content
            byte[] content = "minio-health-check".getBytes(StandardCharsets.UTF_8);

            try (InputStream is = new ByteArrayInputStream(content)) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectName)
                                .stream(is, content.length, -1)
                                .contentType("text/plain")
                                .build()
                );
            }

            // Check file exists
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );

            // Delete dummy file
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );

            return Health.up()
                    .withDetail("bucket", bucket)
                    .withDetail("operation", "upload/stat/delete")
                    .build();

        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("bucket", bucket)
                    .build();
        }
    }
}
