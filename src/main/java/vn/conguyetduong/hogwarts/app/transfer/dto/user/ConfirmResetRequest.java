package vn.conguyetduong.hogwarts.app.transfer.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConfirmResetRequest {
    @NotBlank
    @Email
    String email;
    @NotBlank
    String code;
    @NotBlank
    String newPassword;
}
