package vn.conguyetduong.hogwarts.app.transfer.dto.profile;

import lombok.Data;
import vn.conguyetduong.hogwarts.infra.constant.Accessibility;
import vn.conguyetduong.hogwarts.infra.constant.GenderType;

@Data
public class Gender {
    Accessibility accessibility;
    GenderType gender;
}
