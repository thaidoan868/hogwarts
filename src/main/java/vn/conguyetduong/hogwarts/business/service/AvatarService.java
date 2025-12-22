package vn.conguyetduong.hogwarts.business.service;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.business.service.external.storage.FileService;
import vn.conguyetduong.hogwarts.business.util.UserUtil;
import vn.conguyetduong.hogwarts.business.util.ValidateUtil;
import vn.conguyetduong.hogwarts.infra.model.profile.Avatar;
import vn.conguyetduong.hogwarts.infra.repository.AvatarRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvatarService {
    private final FileService fileService;
    private final AvatarRepository repo;
    private final Logger log = LoggerFactory.getLogger(AvatarService.class);

    public Avatar getCurrentAvatar(UUID userId) {
        return repo.findFirstByUserIdAndIsCurrentTrue(userId).orElseThrow(() ->
                new ApiException(
                        ErrorCode.NOT_FOUND,
                        "User with id %s not found!".formatted(userId.toString())
                )
        );
    }
    public List<Avatar> getAvatars(UUID userId) {
        return repo.findByUserId(userId);
    }


    @Transactional
    public Avatar create(MultipartFile file) {
        // validation
        ValidateUtil.image(file);

        // set names
        String baseName = UUID.randomUUID().toString();
        String mediumObjectName   = baseName + "_medium.jpg";
        String thumbObjectName    = baseName + "_thumb.jpg";

        // generate thumbnail and medium files
        byte[] mediumBytes = resizeImage(file, 400, 400);
        byte[] thumbBytes = resizeImage(file, 100, 100);

        // upload files to Minio
        String originalUrl = fileService.saveFile(file).getUrl();
        String mediumUrl = fileService.saveFile(mediumBytes, mediumObjectName, "image/jpg").getUrl();
        String thumbUrl = fileService.saveFile(thumbBytes, thumbObjectName, "image/jpg").getUrl();

        // set is_current to false for all avatars then save the new one
        repo.unsetCurrentAvatars(UserUtil.getCurrentUserId());

        Avatar avatar = Avatar.builder()
                .originalUrl(originalUrl)
                .mediumUrl(mediumUrl)
                .thumbnailUrl(thumbUrl)
                .isCurrent(true)
                .build();
        return repo.save(avatar);
    }

    private byte[] resizeImage(MultipartFile file, int width, int height) {
        try (InputStream in = file.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Thumbnails.of(in)
                    .size(width, height)
                    .crop(Positions.CENTER)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Failed to resize image", e);
            throw new ApiException(
                    ErrorCode.BAD_REQUEST,
                    "The image could not be resized."
            );
        }
    }
}
