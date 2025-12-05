package vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import vn.conguyetduong.hogwarts.infra.constant.WizardChangeRequestAction;
import vn.conguyetduong.hogwarts.infra.constant.WizardChangeRequestStatus;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class WizardChangeRequestShortResponse {
    private UUID id;
    private String url;
    private String wizardName;
    private WizardChangeRequestAction action;
    private WizardChangeRequestStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
