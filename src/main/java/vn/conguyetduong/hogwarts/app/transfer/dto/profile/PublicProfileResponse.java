package vn.conguyetduong.hogwarts.app.transfer.dto.profile;

import lombok.Data;

import java.util.UUID;

@Data
public class PublicProfileResponse implements ProfileResponse {
    private UUID keycloakUserId;
    private String username;
    private PhoneNumber phoneNumber;
    private BirthDate birthDate;
    private Address address;
    private Gender gender;
}
