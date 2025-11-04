package vn.conguyetduong.hogwarts.app.api.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"/", "/api", "/api/"})
    public String index() {
        return "forward:/index.html";
    }
}
