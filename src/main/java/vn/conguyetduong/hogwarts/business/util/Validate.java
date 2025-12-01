package vn.conguyetduong.hogwarts.business.util;

import org.springframework.web.multipart.MultipartFile;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;

public class Validate {

    public static void image(MultipartFile file) {
        Set<String> ALLOWED_IMAGE_TYPES = Set.of(
                "image/jpeg",
                "image/png",
                "image/webp",
                "image/gif",
                "image/bmp"
        );
        long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB


        if (file.isEmpty()) {
            throw new ApiException(
                    ErrorCode.VALIDATION_FAILED,
                    "The file is empty: " + file.getOriginalFilename()
            );
        }

        if (file.getSize() > MAX_IMAGE_SIZE) {
            throw new ApiException(
                    ErrorCode.EXCEED_MAXSIZE,
                    "Image exceeds maximum size of 10MB: "
                            + file.getOriginalFilename()
            );
        }

        String mime = file.getContentType();
        if (mime == null || !ALLOWED_IMAGE_TYPES.contains(mime)) {
            throw new ApiException(
                    ErrorCode.VALIDATION_FAILED,
                    "Invalid image type: {filename: %s, provided type: %s, allowed_types: %s}".formatted(file.getOriginalFilename(), mime, ALLOWED_IMAGE_TYPES)
            );
        }

        try {
            byte[] bytes = file.getBytes();
            var bais = new ByteArrayInputStream(bytes);
            if (ImageIO.read(bais) == null)
                throw new ApiException(
                        ErrorCode.VALIDATION_FAILED,
                        "Cannot decode the image: " + file.getOriginalFilename()
                );
        } catch (IOException e) {
            throw new ApiException(
                    ErrorCode.VALIDATION_FAILED,
                    "Can not get image bytes: " + file.getOriginalFilename()
            );
        }
    }
}