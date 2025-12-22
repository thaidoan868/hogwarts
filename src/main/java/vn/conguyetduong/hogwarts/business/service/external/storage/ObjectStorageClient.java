package vn.conguyetduong.hogwarts.business.service.external.storage;

import java.io.InputStream;

public interface ObjectStorageClient {
    void putObject(String bucket, String objectName, InputStream inputStream, long sizeBytes, String contentType);
    void statObject(String bucket, String objectName);
    void removeObject(String bucket, String objectName);
}
