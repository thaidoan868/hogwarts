package vn.conguyetduong.hogwarts.app.transfer.dto.wizart;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageRequest {
    @NotBlank
    private String url;
    private String altText;
    private Integer sortOrder;
}
