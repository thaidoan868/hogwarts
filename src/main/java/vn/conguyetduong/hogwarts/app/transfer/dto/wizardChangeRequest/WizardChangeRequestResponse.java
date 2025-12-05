package vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest;

import com.fasterxml.jackson.databind.JsonNode;
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
public class WizardChangeRequestResponse {
    private UUID id;
    private UUID wizardId;
    private WizardChangeRequestAction action;
    private WizardChangeRequestStatus status;
    private JsonNode payload;
    private String createdBy;
    private Instant createdAt;
    private String contributorComment;
    private String reviewedBy;
    private Instant reviewedAt;
    private String reviewComment;
}
