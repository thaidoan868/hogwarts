package vn.conguyetduong.hogwarts.app.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardChangeRequestMapper;
import vn.conguyetduong.hogwarts.business.service.AvatarService;
import vn.conguyetduong.hogwarts.business.service.WizardChangeRequestService;
import vn.conguyetduong.hogwarts.business.service.WizardService;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {
    private final KeycloakService keycloakService;
    private final WizardChangeRequestMapper mapper;
    private final WizardChangeRequestService changeRequestService;
    private final WizardService wizardService;
    private final Validator validator;
    private final ObjectMapper objectMapper;
    private final AvatarService avatarService;

    public ResponseEntity<Object> test() {
        Object result = null;
        return ResponseEntity.ok(result);
    }
}
