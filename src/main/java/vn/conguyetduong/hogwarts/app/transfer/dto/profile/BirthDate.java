package vn.conguyetduong.hogwarts.app.transfer.dto.profile;

import lombok.Data;
import vn.conguyetduong.hogwarts.infra.constant.Accessibility;

import java.time.LocalDate;

@Data
public class BirthDate {
    Accessibility accessibility;
    LocalDate birthDate;
}
