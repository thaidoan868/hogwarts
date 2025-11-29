package vn.conguyetduong.hogwarts.app.api.v1.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeRequestResponse;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeRequestShortResponse;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardChangeRequestMapper;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.business.service.WizardChangeRequestService;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.business.util.UserUtil;
import vn.conguyetduong.hogwarts.infra.model.WizardChangeRequest;
import vn.conguyetduong.hogwarts.infra.repository.WizardRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/wizard-change-requests")
@RequiredArgsConstructor
public class AdminWizardRequestChangeController {
    private final WizardChangeRequestService service;
    private final WizardChangeRequestMapper mapper;
    private final WizardRepository repo;
    private final KeycloakService keycloakService;

    @GetMapping
    public ResponseEntity<List<WizardChangeRequestShortResponse>> list() {
        List<WizardChangeRequest> requests = service.listAll();
        List<WizardChangeRequestShortResponse> responses = mapper.toWizardChangeRequestShortResponses(requests, repo);
        responses.forEach(response -> {
            String url = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/wizard-change-requests/{id}")
                    .buildAndExpand(response.getId())
                    .toString();
            response.setUrl(url);
        });
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WizardChangeRequestResponse> get(@PathVariable UUID id) {
        WizardChangeRequest request = service.get(id);
        WizardChangeRequestResponse response = mapper.toWizardChangeRequestResponse(request, keycloakService);
        return  ResponseEntity.ok(response);
    }
}
