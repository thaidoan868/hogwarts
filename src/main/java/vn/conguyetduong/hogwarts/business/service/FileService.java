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
import vn.conguyetduong.hogwarts.business.util.UserUtil;
import vn.conguyetduong.hogwarts.business.util.Validate;
import vn.conguyetduong.hogwarts.infra.model.Image;
import vn.conguyetduong.hogwarts.infra.repository.ImageRepository;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final MinioClient minioClient;
    private final ImageRepository imageRepo;

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    @Value("${minio.bucket}")
    private String bucket;
    @Value("${minio.endpoint}")
    private String minioEndpoint;
    @Value("${resources.imageResourceUrl}")
    private String imageResourceUrl;


    @Transactional
    public List<Image> saveImages(List<MultipartFile> files) {
        List<Image> savedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            Validate.image(file);

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

                String encodedName = UriUtils.encodePathSegment(objectName, StandardCharsets.UTF_8);
                String url = UriComponentsBuilder
                        .fromUriString(imageResourceUrl)
                        .pathSegment(bucket)
                        .pathSegment(encodedName)
                        .toUriString();

                Image image = Image.builder()
                        .bucket(bucket)
                        .objectName(objectName)
                        .url(url)
                        .contentType(file.getContentType())
                        .sizeBytes(file.getSize())
                        .build();

                savedImages.add(imageRepo.save(image));

            } catch (Exception e) {
                log.error("Failed to upload image file", e);

                throw new ApiException(ErrorCode.INTERNAL_ERROR, "The image is valid but the system failed to upload the image file: %s".formatted(file.getOriginalFilename()));
            }
        }

        return savedImages;
    }
}

