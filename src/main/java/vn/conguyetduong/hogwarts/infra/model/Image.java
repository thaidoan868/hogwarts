package vn.conguyetduong.hogwarts.infra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Image {

    @Id
    @UuidGenerator
    private UUID id;

    @CreatedBy
    private UUID uploaderId;

    @NotBlank
    private String bucket;

    @NotBlank
    private String objectName;

    @NotBlank
    private String url;

    private String contentType;

    private Long sizeBytes;

    @CreatedDate
    private Instant createdAt;
}
