package vn.conguyetduong.hogwarts.app.transfer.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FileImageResponse {
    private UUID id;
    private String url;
    private String contentType;
    private Long sizeBytes;
}
