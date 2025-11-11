package vn.conguyetduong.hogwarts.app.api.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/")
public class AuthController {
    @PostMapping("register")
    public String register() {
        return "register";
    }
}
