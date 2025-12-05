package vn.conguyetduong.hogwarts.app.transfer.dto.wizart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import vn.conguyetduong.hogwarts.infra.model.WizardImage;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ShortWizardResponse {
    private UUID id;
    private String name;
    private String url;
    private List<ImageResponse> images;
}
