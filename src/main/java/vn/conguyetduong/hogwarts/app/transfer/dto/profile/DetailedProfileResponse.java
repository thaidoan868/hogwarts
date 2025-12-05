package vn.conguyetduong.hogwarts.app.transfer.dto.profile;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class DetailedProfileResponse implements ProfileResponse {
    private UUID keycloakUserId;
    private String username;
    private PhoneNumber phoneNumber;
    private BirthDate birthDate;
    private Address address;
    private Gender gender;
    private Instant createdAt;
    private Instant updatedAt;
}
