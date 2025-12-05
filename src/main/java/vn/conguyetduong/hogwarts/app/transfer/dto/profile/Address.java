package vn.conguyetduong.hogwarts.app.transfer.dto.profile;

import lombok.Data;
import vn.conguyetduong.hogwarts.infra.constant.Accessibility;

@Data
public class Address {
    Accessibility accessibility;
    String address;
}
