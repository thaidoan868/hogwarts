package vn.conguyetduong.hogwarts.app.transfer.dto.wizart;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import vn.conguyetduong.hogwarts.infra.model.WizardImage;

import java.util.List;

@Data
public class RegisterWizardRequest {
    @NotBlank
    private String name;
    private String description;
    private List<ImageRequest> images;
}
