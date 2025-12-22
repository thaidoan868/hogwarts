package vn.conguyetduong.hogwarts.business.service.external.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@Profile("staging")
@RequiredArgsConstructor
public class S3ObjectStorageClient implements ObjectStorageClient {

    private final S3ObjectStorageClient s3Client;

    @Override
    public void putObject(String bucket, String objectName, InputStream inputStream, long sizeBytes, String contentType) {
        s3Client.putObject(bucket, objectName, inputStream, sizeBytes, contentType);
    }

    @Override
    public void statObject(String bucket, String objectName) {
        s3Client.statObject(bucket, objectName);
    }

    @Override
    public void removeObject(String bucket, String objectName) {
        s3Client.removeObject(bucket, objectName);
    }
}
