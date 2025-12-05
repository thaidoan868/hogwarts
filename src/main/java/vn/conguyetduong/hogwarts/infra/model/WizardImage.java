package vn.conguyetduong.hogwarts.infra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class WizardImage {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wizard_id")
    @NotNull
    @ToString.Exclude
    private Wizard wizard;

    @NotBlank
    private String url;

    private String altText;

    @NotNull
    @Min(0)
    @Builder.Default
    private Integer sortOrder = 0;

    @CreatedDate
    private Instant createdAt;

    @CreatedBy
    private UUID createdBy;
}

