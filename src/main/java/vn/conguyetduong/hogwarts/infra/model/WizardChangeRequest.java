package vn.conguyetduong.hogwarts.infra.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.conguyetduong.hogwarts.infra.constant.WizardChangeRequestAction;
import vn.conguyetduong.hogwarts.infra.constant.WizardChangeRequestStatus;

import java.sql.Types;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class WizardChangeRequest {
    @Id
    @UuidGenerator
    private UUID id;

    private UUID wizardId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private WizardChangeRequestAction action;

    @NotNull
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private WizardChangeRequestStatus status = WizardChangeRequestStatus.PENDING;

//    @Convert(converter = JsonNodeConverter.class)
    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private JsonNode payload;

    @NotNull
    @CreatedBy
    private UUID createdBy;

    @CreatedDate
    private Instant createdAt;

    private String contributorComment;

    @LastModifiedBy
    @Builder.Default
    private UUID reviewedBy = null;

    @LastModifiedDate
    private Instant reviewedAt;

    private String reviewComment;
}