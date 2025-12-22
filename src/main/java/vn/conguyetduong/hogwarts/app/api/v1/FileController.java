package vn.conguyetduong.hogwarts.app.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.conguyetduong.hogwarts.app.transfer.dto.file.FileResponse;
import vn.conguyetduong.hogwarts.app.transfer.mapper.FileMapper;
import vn.conguyetduong.hogwarts.business.service.external.storage.FileService;
import vn.conguyetduong.hogwarts.business.util.ValidateUtil;
import vn.conguyetduong.hogwarts.infra.model.File;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService service;
    private final FileMapper mapper;

    @PostMapping(path = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileResponse>> uploadImages(@RequestParam List<MultipartFile> files) {
        List<File> images = new ArrayList();

        for (MultipartFile file : files) {
            ValidateUtil.image(file);
            images.add(service.saveFile(file));
        }

        // convert and return
        List<FileResponse> response = mapper.toFileResponseList(images);
        return ResponseEntity.ok(response);
    }
}
