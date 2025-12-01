package vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest;

import lombok.Data;
import vn.conguyetduong.hogwarts.infra.constant.WizardChangeRequestStatus;

@Data
public class WizardChangeRequestUpdateRequest {
    private WizardChangeRequestStatus status;
    private String reviewComment;
}
