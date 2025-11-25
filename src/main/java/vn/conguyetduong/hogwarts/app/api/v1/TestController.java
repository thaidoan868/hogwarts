package vn.conguyetduong.hogwarts.app.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.model.User;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {
    private final KeycloakService keycloakService;

    @GetMapping
    public String test() {
//        User user = keycloakService.getUser(UUID.fromString("2a7f5aa9-b4b9-4e58-aaab-36f4814c3294"));
        User user = keycloakService.getUser(UUID.fromString("2a7f5aa9-b4b9-4e58-aaab-36f4814c329"));

        String result = user.toString();
        return result;
    }
}
