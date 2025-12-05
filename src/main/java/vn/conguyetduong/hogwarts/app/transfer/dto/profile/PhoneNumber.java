package vn.conguyetduong.hogwarts.app.transfer.dto.profile;

import lombok.Data;
import vn.conguyetduong.hogwarts.business.annotation.PhoneNumberValidate;
import vn.conguyetduong.hogwarts.infra.constant.Accessibility;
@Data
public class PhoneNumber {
    Accessibility accessibility;

    @PhoneNumberValidate
    String phoneNumber;
}
