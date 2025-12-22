package vn.conguyetduong.hogwarts.infra.client.objectstorage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.Objects;

@Component
@Profile("staging")
@RequiredArgsConstructor
public class S3ObjectStorageClient implements ObjectStorageClient {

    private final S3Client s3Client;

    @Override
    public void putObject(
            String bucket,
            String objectName,
            InputStream inputStream,
            long sizeBytes,
            String contentType
    ) {
        Objects.requireNonNull(bucket, "bucket must not be null");
        Objects.requireNonNull(objectName, "objectName must not be null");
        Objects.requireNonNull(inputStream, "inputStream must not be null");

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectName)
                .contentType(contentType)
                .contentLength(sizeBytes)
                .build();

        s3Client.putObject(request, RequestBody.fromInputStream(inputStream, sizeBytes));
    }

    @Override
    public void statObject(String bucket, String objectName) {
        Objects.requireNonNull(bucket, "bucket must not be null");
        Objects.requireNonNull(objectName, "objectName must not be null");

        HeadObjectRequest request = HeadObjectRequest.builder()
                .bucket(bucket)
                .key(objectName)
                .build();

        s3Client.headObject(request);
    }

    @Override
    public void removeObject(String bucket, String objectName) {
        Objects.requireNonNull(bucket, "bucket must not be null");
        Objects.requireNonNull(objectName, "objectName must not be null");

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(objectName)
                .build();

        s3Client.deleteObject(request);
    }
}
