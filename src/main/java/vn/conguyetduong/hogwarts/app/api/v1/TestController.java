package vn.conguyetduong.hogwarts.app.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.RegisterWizardChangeRequestRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.WizardPatchUpdateRequest;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardChangeRequestMapper;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.business.service.WizardChangeRequestService;
import vn.conguyetduong.hogwarts.business.service.WizardService;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.model.Wizard;
import vn.conguyetduong.hogwarts.infra.model.WizardChangeRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    @GetMapping
    public String test() {
        return "result";
    }
}
