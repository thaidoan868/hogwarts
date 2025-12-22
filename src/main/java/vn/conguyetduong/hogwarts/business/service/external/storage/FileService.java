package vn.conguyetduong.hogwarts.business.service.external.storage;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.instrumentation.annotations.WithSpan;
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
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class FileService {

    private final ObjectStorageClient objectStorageClient;
    private final FileRepository fileRepository;

    private final Logger log = LoggerFactory.getLogger(FileService.class);
    private final Tracer tracer;
    @Value("${resources.bucket}")
    private String bucket;

    @Value("${resources.baseResourceUrl}")
    private String baseResourceUrl;

    @Transactional
    @WithSpan
    public File saveFile(MultipartFile file) {
        String original = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
        String fileName = original.trim().replaceAll("\\s+", "_");
        String objectName = "%s_%s".formatted(UUID.randomUUID(), fileName);

        try (InputStream is = file.getInputStream()) {
            objectStorageClient.putObject(bucket, objectName, is, file.getSize(), file.getContentType());
        } catch (Exception e) {
            log.error("Failed to upload file", e);
            throw new ApiException(
                    ErrorCode.INTERNAL_ERROR,
                    "Failed to upload the file: %s".formatted(original)
            );
        }
        return fileRepository.save(buildFileEntity(objectName, file.getContentType(), file.getSize()));
    }

    @Transactional
    public File saveFile(byte[] file, String objectName, String contentType) {
        try (InputStream is = new ByteArrayInputStream(file)) {
            objectStorageClient.putObject(bucket, objectName, is, file.length, contentType);
        } catch (Exception e) {
            log.error("Failed to upload bytes", e);
            throw new ApiException(ErrorCode.INTERNAL_ERROR, "Couldn't upload file");
        }

        return fileRepository.save(buildFileEntity(objectName, contentType, (long) file.length));
    }

    private File buildFileEntity(String objectName, String contentType, long sizeBytes) {
        String encodedName = UriUtils.encodePathSegment(objectName, UTF_8);

        String url = UriComponentsBuilder
                .fromUriString(baseResourceUrl)
                .pathSegment(bucket)
                .pathSegment(encodedName)
                .toUriString();

        return File.builder()
                .bucket(bucket)
                .objectName(objectName)
                .url(url)
                .contentType(contentType)
                .sizeBytes(sizeBytes)
                .build();
    }
}