package vn.conguyetduong.hogwarts.app.transfer.dto.wizart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ImageRequest {
    @NotBlank
    private String url;
    private String altText;
    private Integer sortOrder;
}
