package vn.conguyetduong.hogwarts.app.api.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeReqRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.RegisterWizardRequest;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardChangeRequestMapper;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.model.User;
import vn.conguyetduong.hogwarts.infra.model.WizardChangeRequest;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {
    private final KeycloakService keycloakService;
    private final WizardChangeRequestMapper mapper;

    @GetMapping
    public String test(@RequestBody @Valid WizardChangeReqRequest request) {
        WizardChangeRequest wizardRequest = mapper.toWizardChangeRequest(request);
        return "result";
    }
}
