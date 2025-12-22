package vn.conguyetduong.hogwarts.business.service.external.storage;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class MinIoObjectStorageClient implements ObjectStorageClient {

    private final MinioClient minioClient;

    @Override
    public void putObject(String bucket, String objectName, InputStream inputStream, long sizeBytes, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .contentType(contentType)
                            .stream(inputStream, sizeBytes, -1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("MinIO upload failed", e);
        }
    }

    @Override
    public void statObject(String bucket, String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
        }  catch (Exception e) {throw new RuntimeException("MinIO statObject failed", e);}
    }

    @Override
    public void removeObject(String bucket, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {throw new RuntimeException("MinIO remove failed", e);}
    }
}

