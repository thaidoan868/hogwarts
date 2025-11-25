package vn.conguyetduong.hogwarts.infra.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.conguyetduong.hogwarts.infra.constant.WizardStatus;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Wizard {

    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank
    @Size(max = 255)
    private String name;

    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private WizardStatus status = WizardStatus.ACTIVE;

    @NotNull
    @Builder.Default
    private Long viewCount = 0L;

    @CreatedDate
    private Instant createdAt;

    @NotNull
    @CreatedBy
    private UUID createdBy;

    @LastModifiedDate
    private Instant updatedAt;

    @NotNull
    @LastModifiedBy
    private UUID updatedBy;

    @OneToMany(mappedBy = "wizard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WizardImage> images;
}

