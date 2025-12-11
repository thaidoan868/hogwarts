package vn.conguyetduong.hogwarts.app.transfer.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
public class PasswordResetRequest {

    @NotNull
    @Schema(description = "The username of the user requesting the password reset", defaultValue = "alice123")
    private String username;

    @NotNull
    @Email
    @Schema(description = "The email address associated with the username", defaultValue = "alice@example.com")
    private String email;
}

