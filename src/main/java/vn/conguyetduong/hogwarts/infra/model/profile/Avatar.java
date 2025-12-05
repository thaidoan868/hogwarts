package vn.conguyetduong.hogwarts.infra.model.profile;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.conguyetduong.hogwarts.infra.constant.Accessibility;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Avatar {
    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    @CreatedBy
    private UUID userId;

    @NotNull
    private String originalUrl;

    @NotNull
    private String mediumUrl;

    @NotNull
    private String thumbnailUrl;

    @CreationTimestamp
    private Instant createdAt;

    @NotNull
    @Builder.Default
    private boolean isCurrent = true;
}
