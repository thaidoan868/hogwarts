package vn.conguyetduong.hogwarts.app.api.v1.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeRequestResponse;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeRequestShortResponse;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeRequestUpdateRequest;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardChangeRequestMapper;
import vn.conguyetduong.hogwarts.business.service.WizardChangeRequestService;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.model.WizardChangeRequest;
import vn.conguyetduong.hogwarts.infra.repository.WizardChangeRequestRepository;
import vn.conguyetduong.hogwarts.infra.repository.WizardRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/wizard-change-requests")
@RequiredArgsConstructor
public class AdminWizardRequestChangeController {
    private final WizardChangeRequestService service;
    private final WizardChangeRequestMapper mapper;
    private final WizardRepository wizardRepo;
    private final WizardChangeRequestRepository repo;
    private final KeycloakService keycloakService;

    @GetMapping
    public ResponseEntity<List<WizardChangeRequestShortResponse>> list() {
        List<WizardChangeRequest> requests = service.listAll();
        List<WizardChangeRequestShortResponse> responses = mapper.toWizardChangeRequestShortResponses(requests, wizardRepo);
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

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUpdate(@PathVariable UUID id, @RequestBody WizardChangeRequestUpdateRequest updateRequest) {
        WizardChangeRequest oldRequest = service.get(id);



        WizardChangeRequest updatedRequest = mapper.patchUpdateRequest(updateRequest, oldRequest);
        repo.save(updatedRequest);
        return ResponseEntity.ok().build();
    }


}
