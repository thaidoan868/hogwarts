package vn.conguyetduong.hogwarts.app.api.v1;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.conguyetduong.hogwarts.app.transfer.dto.user.ConfirmResetRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.user.PasswordResetRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.user.RegisterUserRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.user.RegisterUserResponse;
import vn.conguyetduong.hogwarts.app.transfer.mapper.UserMapper;
import vn.conguyetduong.hogwarts.business.service.UserService;
import vn.conguyetduong.hogwarts.infra.model.User;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication endpoints", description = "Handles user requests for password resets, including validating and processing reset requests.")
public class AuthController {
    private final UserService service;
    private final UserMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest userRequest) {
        User user = mapper.toUser(userRequest);
        User createdUser = service.register(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/api/v1/users/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
    @WithSpan
    @PostMapping("/password/reset/request")
    @Operation(summary = "Request to reset password", description = "Check the email and send the code back to the server")
    public ResponseEntity<?> requestResetCode(@Valid @RequestBody PasswordResetRequest request) {
        // @Parameter(description = "The email address associated with the username") @RequestParam String email
        service.requestResetCode(request.getUsername(), request.getEmail());
        return ResponseEntity.noContent().build();
   }

    @PostMapping("/password/reset/confirm")
    public ResponseEntity<?> confirmReset(@Valid @RequestBody ConfirmResetRequest request) {
        service.confirmResetPassword(request.getEmail(), request.getCode(), request.getNewPassword());
        return ResponseEntity.noContent().build();
    }
}