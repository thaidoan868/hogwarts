package vn.conguyetduong.hogwarts.app.transfer.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordResetRequest {
    @NotNull
    String username;
    @NotNull
    @Email
    String email;
}
