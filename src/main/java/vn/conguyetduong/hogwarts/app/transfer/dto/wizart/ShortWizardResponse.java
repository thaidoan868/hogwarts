package vn.conguyetduong.hogwarts.app.transfer.dto.wizart;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(defaultValue = "cc4124b2-9e88-4e31-9520-0d178cfab624")
    private UUID id;
    @Schema(defaultValue = "Harry Potter")
    private String name;
    @Schema(defaultValue = "http://localhost:8081/api/v1/wizards/cc4124b2-9e88-4e31-9520-0d178cfab624")
    private String url;
    private List<ImageResponse> images;
}
