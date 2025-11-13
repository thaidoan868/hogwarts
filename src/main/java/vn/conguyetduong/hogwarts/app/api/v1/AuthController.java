package vn.conguyetduong.hogwarts.app.api.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
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
public class AuthController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest userRequest) {
        User user = mapper.toUser(userRequest);
        User createdUser = service.register(user);
        URI location = URI.create("/api/v1/users/" + createdUser.getId());

        return ResponseEntity.created(location).build();
    }
}