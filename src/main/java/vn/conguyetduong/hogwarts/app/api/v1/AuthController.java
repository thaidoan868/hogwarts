package vn.conguyetduong.hogwarts.app.api.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.conguyetduong.hogwarts.app.transfer.dto.user.RegisterUserRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.user.RegisterUserResponse;
import vn.conguyetduong.hogwarts.app.transfer.mapper.UserMapper;
import vn.conguyetduong.hogwarts.business.service.UserService;
import vn.conguyetduong.hogwarts.infra.model.User;

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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterUserResponse(createdUser.getId()));
    }
}