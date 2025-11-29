package vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.RegisterWizardRequest;
import vn.conguyetduong.hogwarts.infra.constant.WizardChangeRequestAction;

import java.util.UUID;

@Data
public class WizardChangeReqRequest {
    @NotNull
    private UUID wizardId;
    @NotNull
    private WizardChangeRequestAction action;
    @NotNull
    private RegisterWizardRequest payload;
    private String contributorComment;
}
