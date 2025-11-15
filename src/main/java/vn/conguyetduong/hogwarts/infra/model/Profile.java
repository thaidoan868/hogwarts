package vn.conguyetduong.hogwarts.infra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Profile {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    private UUID keycloakUserId;

    @NotBlank
    private String keycloakRealm = "hogwarts";

    private String phoneNumber;

    private String address;

    private String avatarUrl;

    private LocalDate dateOfBirth;

    private String gender;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Profile(UUID keycloakUserId) {
        this.keycloakUserId = keycloakUserId;
    }
}
