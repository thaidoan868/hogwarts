package vn.conguyetduong.hogwarts.app.transfer.dto.wizart;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WizardPatchUpdateRequest {
    private String name;
    private String description;
    private List<ImageRequest> images;
}