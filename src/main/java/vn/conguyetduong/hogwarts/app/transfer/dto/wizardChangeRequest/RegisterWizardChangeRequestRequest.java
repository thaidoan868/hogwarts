package vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.RegisterWizardRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.WizardPatchUpdateRequest;
import vn.conguyetduong.hogwarts.infra.constant.WizardChangeRequestAction;

import java.util.UUID;

@Data
public class RegisterWizardChangeRequestRequest {
    @NotNull
    private UUID wizardId;
    @NotNull
    private WizardChangeRequestAction action;
    @NotNull
    private WizardPatchUpdateRequest payload;
    private String contributorComment;
}
