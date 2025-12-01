package vn.conguyetduong.hogwarts.app.api.v1;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.conguyetduong.hogwarts.app.transfer.dto.ErrorResponse;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;

@Controller
public class AppController {
    @GetMapping({"/", "/api", "/api/"})
    public String index() {
        return "forward:/index.html";
    }
}
