package vn.conguyetduong.hogwarts.app.transfer.dto.profile;

import lombok.Data;

@Data
public class ProfileRequest {
    private PhoneNumber phoneNumber;
    private BirthDate birthDate;
    private Address address;
    private Gender gender;
}
