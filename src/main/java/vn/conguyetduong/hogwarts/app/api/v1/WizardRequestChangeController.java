package vn.conguyetduong.hogwarts.app.api.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeReqRequest;
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

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wizard-change-requests")
@RequiredArgsConstructor
public class WizardRequestChangeController {
    private final WizardChangeRequestService service;
    private final WizardChangeRequestMapper mapper;
    private final WizardRepository repo;
    private final KeycloakService keycloakService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid WizardChangeReqRequest request) {
        WizardChangeRequest wizardRequest = mapper.toWizardChangeRequest(request);
        WizardChangeRequest createdWizardRequest = service.create(wizardRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/wizard-change-requests/{id}")
                .buildAndExpand(createdWizardRequest.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<WizardChangeRequestShortResponse>> list() {
        List<WizardChangeRequest> requests = service.list();
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
        if (!request.getCreatedBy().equals(UserUtil.getCurrentUserId())) {
            throw new ApiException(ErrorCode.FORBIDDEN, "You don't have permission to access this resource");
        }
        WizardChangeRequestResponse response = mapper.toWizardChangeRequestResponse(request, keycloakService);
        return  ResponseEntity.ok(response);
    }
}
