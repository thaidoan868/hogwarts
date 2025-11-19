package vn.conguyetduong.hogwarts.app.transfer.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ImageResponse {
    private UUID id;
    private String url;
    private String contentType;
    private Long sizeBytes;
}
