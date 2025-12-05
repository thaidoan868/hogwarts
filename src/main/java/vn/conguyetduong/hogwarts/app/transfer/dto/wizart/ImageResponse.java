package vn.conguyetduong.hogwarts.app.transfer.dto.wizart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import vn.conguyetduong.hogwarts.infra.model.Wizard;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ImageResponse {
    private UUID id;
    private String url;
    private String altText;
    private Integer sortOrder;
}
