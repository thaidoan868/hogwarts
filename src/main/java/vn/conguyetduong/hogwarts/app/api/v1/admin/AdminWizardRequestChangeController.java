package vn.conguyetduong.hogwarts.app.api.v1.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeRequestResponse;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeRequestShortResponse;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeRequestUpdateRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.RegisterWizardRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.WizardPatchUpdateRequest;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardChangeRequestMapper;
import vn.conguyetduong.hogwarts.app.transfer.mapper.WizardMapper;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.business.service.WizardChangeRequestService;
import vn.conguyetduong.hogwarts.business.service.WizardService;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.constant.WizardChangeRequestStatus;
import vn.conguyetduong.hogwarts.infra.model.Wizard;
import vn.conguyetduong.hogwarts.infra.model.WizardChangeRequest;
import vn.conguyetduong.hogwarts.infra.model.WizardImage;
import vn.conguyetduong.hogwarts.infra.repository.WizardChangeRequestRepository;
import vn.conguyetduong.hogwarts.infra.repository.WizardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    private final WizardService wizardService;
    private final WizardMapper  wizardMapper;
    private final ObjectMapper objectMapper = new  ObjectMapper();
    private final Logger log = LoggerFactory.getLogger(AdminWizardRequestChangeController.class);
    private final Validator validator;

    @GetMapping
    public ResponseEntity<List<WizardChangeRequestShortResponse>> list() {
        log.trace("GET /api/v1/admin/wizard-change-requests");
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
        WizardChangeRequestResponse response = mapper.toWizardChangeRequestResponse(request);
        return  ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUpdate(@PathVariable UUID id, @RequestBody WizardChangeRequestUpdateRequest updateRequest) {
        WizardChangeRequest oldRequest = service.get(id);

        if (updateRequest.getStatus() == WizardChangeRequestStatus.APPROVED) {
            switch (oldRequest.getAction()) {
                case UPDATE: {
                    Wizard wizard = wizardService.getWizard(oldRequest.getWizardId());

                    // convert payload to update wizard request
                    WizardPatchUpdateRequest updateWizardRequest;
                    JsonNode payload = oldRequest.getPayload();
                    try {
                        updateWizardRequest = objectMapper.treeToValue(payload, WizardPatchUpdateRequest.class);
                    } catch (IllegalArgumentException | JsonProcessingException e) {
                        log.error("An error occurred while processing the payload.", e);
                        throw new ApiException(
                                ErrorCode.BAD_REQUEST,
                                "Can not convert wizard payload to wizard patch update request"
                        );
                    }

                    // validation
                    Set<ConstraintViolation<WizardPatchUpdateRequest>> violations = validator.validate(updateWizardRequest);
                    if (!violations.isEmpty()) {
                        List<String> errors = new ArrayList<>();
                        for (ConstraintViolation<WizardPatchUpdateRequest> violation : violations) {
                            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
                        }
                        throw new ApiException(
                                ErrorCode.VALIDATION_FAILED,
                                "Couldn't update the wizard because the change request violated %d constraint(s)".formatted(violations.size()),
                                errors
                        );
                    }

                    // mapping
                    Wizard updateWizard = wizardMapper.patchUpdateWizard(updateWizardRequest, wizard);
                    List<WizardImage> images = wizardMapper.toWizardImageList(updateWizardRequest.getImages());
                    if (images != null) {
                        images.forEach(image -> image.setWizard(wizard));
                    }

                    if (wizard.getImages() != null) {
                        if (images != null) {
                            wizard.getImages().clear();
                            wizard.getImages().addAll(images);
                        }
                    } else {
                        wizard.setImages(images);
                    }

                    // updating
                    Wizard updatedWizard = wizardService.update(updateWizard);
                    break;
                }

                case DELETE: {
                    wizardService.delete(oldRequest.getWizardId());
                    break;
                }

                case CREATE: {
                    // convert payload to register wizard request
                    RegisterWizardRequest wizardRequest;
                    JsonNode payload = oldRequest.getPayload();
                    try {
                        wizardRequest = objectMapper.treeToValue(payload, RegisterWizardRequest.class);
                    } catch (IllegalArgumentException | JsonProcessingException e) {
                        log.error("An error occurred while processing the payload.", e);
                        throw new ApiException(
                                ErrorCode.BAD_REQUEST,
                                "Can not convert wizard payload to register wizard  request"
                        );
                    }

                    // validation
                    Set<ConstraintViolation<RegisterWizardRequest>> violations = validator.validate(wizardRequest);
                    if (!violations.isEmpty()) {
                        List<String> errors = new ArrayList<>();
                        for (ConstraintViolation<RegisterWizardRequest> violation : violations) {
                            errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
                        }
                        throw new ApiException(
                                ErrorCode.VALIDATION_FAILED,
                                "Couldn't create the wizard because the register request violated %d constraint(s)".formatted(violations.size()),
                                errors
                        );
                    }

                    // creation
                    Wizard createWizard = wizardMapper.toWizard(wizardRequest);
                    Wizard createdWizard = wizardService.create(createWizard);
                    break;
                }

                default: {
                    throw new ApiException(
                            ErrorCode.BAD_REQUEST,
                            "Unsupported wizard change request action: " + oldRequest.getAction()
                    );
                }
            }

        }

        WizardChangeRequest updatedRequest = mapper.patchUpdateRequest(updateRequest, oldRequest);
        service.update(updatedRequest);
        return ResponseEntity.noContent().build();
    }
}
