package vn.conguyetduong.hogwarts.app.transfer.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class FileResponse {
    private UUID id;
    private String url;
    private String contentType;
    private Long sizeBytes;
}
