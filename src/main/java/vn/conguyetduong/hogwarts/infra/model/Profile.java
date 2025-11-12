package vn.conguyetduong.hogwarts.infra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotBlank
    private UUID keycloakUserId;              // token.getSubject() → UUID.fromString(sub)

    @NotBlank
    private String keycloakRealm = "hogwarts";

    @NotBlank
    private String keycloakUsername;          // preferred_username

    private String fullName;

    private String phoneNumber;

    private String address;

    private String avatarUrl;

    private LocalDate dateOfBirth;

    private String gender;

    @CreatedDate
    private OffsetDateTime createdAt;

    @LastModifiedDate
    private OffsetDateTime updatedAt;

    public Profile(UUID keycloakUserId, String keycloakUsername) {
        this.keycloakUserId = keycloakUserId;
        this.keycloakUsername = keycloakUsername;
    }
}
