package vn.conguyetduong.hogwarts.app.transfer.dto.wizart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import vn.conguyetduong.hogwarts.infra.model.WizardImage;

import java.util.List;
import java.util.UUID;

@Data
public class WizardRequest {
    @NotBlank
    private String name;
    private String description;
    private List<WizardImage> images;
}
