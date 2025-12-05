package vn.conguyetduong.hogwarts.business.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.infra.model.File;
import vn.conguyetduong.hogwarts.infra.repository.FileRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {
    private final MinioClient minioClient;
    private final FileRepository fileRepository;

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    @Value("${minio.bucket}")
    private String bucket;
    @Value("${minio.endpoint}")
    private String minioEndpoint;
    @Value("${resources.baseResourceUrl}")
    private String baseResourceUrl;


    @Transactional
    public File saveFile(MultipartFile file) {
            String fileName = file.getOriginalFilename().trim().replaceAll("\\s+", "_");;
            String objectName = "%s_%s".formatted(UUID.randomUUID(), fileName);

            try (InputStream is = file.getInputStream()) {
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectName)
                        .contentType(file.getContentType())
                        .stream(is, file.getSize(), -1)
                        .build()
                );
            } catch (Exception e) {
                log.error("Failed to upload file", e);
                throw new ApiException(ErrorCode.INTERNAL_ERROR, "Failed to upload the file: %s".formatted(file.getOriginalFilename()));
            }

            String encodedName = UriUtils.encodePathSegment(objectName, UTF_8);
            String url = UriComponentsBuilder
                    .fromUriString(baseResourceUrl)
                    .pathSegment(bucket)
                    .pathSegment(encodedName)
                    .toUriString();

            File fileObject = File.builder()
                    .bucket(bucket)
                    .objectName(objectName)
                    .url(url)
                    .contentType(file.getContentType())
                    .sizeBytes(file.getSize())
                    .build();

            return fileRepository.save(fileObject);
    }

    @Transactional
    public File saveFile(byte[] file, String objectName, String contentType) {
        try (InputStream is =  new ByteArrayInputStream(file)) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(is, file.length, -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            throw new ApiException(ErrorCode.INTERNAL_ERROR, "Couldn't upload to MinIO");
        }

        String encodedName = UriUtils.encodePathSegment(objectName, UTF_8);
        String url = UriComponentsBuilder
                .fromUriString(baseResourceUrl)
                .pathSegment(bucket)
                .pathSegment(encodedName)
                .toUriString();

        File fileObject = File.builder()
                .bucket(bucket)
                .objectName(objectName)
                .url(url)
                .contentType(contentType)
                .sizeBytes((long) file.length)
                .build();

        return fileRepository.save(fileObject);
    }
}

