package vn.conguyetduong.hogwarts.app.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.conguyetduong.hogwarts.app.transfer.dto.file.ImageResponse;
import vn.conguyetduong.hogwarts.business.service.FileService;
import vn.conguyetduong.hogwarts.infra.model.Image;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor

public class FileController {
    private final FileService fileService;

    @PostMapping(path = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ImageResponse>> uploadImages(
            @RequestPart("images") List<MultipartFile> files
    ) {
        List<Image> images = fileService.saveImages(files);
        var response = images.stream()
                .map(image -> new ImageResponse(
                        image.getId(),
                        image.getUrl(),
                        image.getContentType(),
                        image.getSizeBytes()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
