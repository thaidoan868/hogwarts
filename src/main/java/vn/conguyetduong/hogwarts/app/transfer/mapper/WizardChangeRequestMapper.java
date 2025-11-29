package vn.conguyetduong.hogwarts.app.transfer.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeReqRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeRequestResponse;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizardChangeRequest.WizardChangeRequestShortResponse;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.RegisterWizardRequest;
import vn.conguyetduong.hogwarts.business.exception.ApiException;
import vn.conguyetduong.hogwarts.business.exception.ErrorCode;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.model.Wizard;
import vn.conguyetduong.hogwarts.infra.model.WizardChangeRequest;
import vn.conguyetduong.hogwarts.infra.repository.WizardRepository;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface WizardChangeRequestMapper {
    @Mapping(target = "payload", source = "payload", qualifiedByName = "mapPayload")
    WizardChangeRequest toWizardChangeRequest(WizardChangeReqRequest request);

    @Named("mapPayload")
    default JsonNode mapPayload(RegisterWizardRequest registerWizard) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(registerWizard);
    }

    // ----

    List<WizardChangeRequestShortResponse> toWizardChangeRequestShortResponses(List<WizardChangeRequest> requests, @Context WizardRepository repo);

    @Mapping(target = "wizardName", source = "wizardId", qualifiedByName = "mapWizardIdToName")
    WizardChangeRequestShortResponse toWizardChangeRequestShortResponse(WizardChangeRequest request, @Context WizardRepository repo);

    @Named("mapWizardIdToName")
    default String mapWizardIdToName(UUID id, @Context WizardRepository repo) {
        Wizard wizard = repo.findById(id).orElseThrow(() ->
                        new ApiException(
                                ErrorCode.NOT_FOUND,
                                "Wizard with id '%s' not found".formatted(id)
                        )
                );
        return wizard.getName();
    }

    // ---
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "mapIdToUsername")
    @Mapping(target = "reviewedBy", source = "reviewedBy", qualifiedByName = "mapIdToUsername")
    WizardChangeRequestResponse toWizardChangeRequestResponse(WizardChangeRequest request, @Context KeycloakService  keycloakService);

    @Named("mapIdToUsername")
    default String mapIdToUsername(UUID id, @Context KeycloakService keycloakService) {
        return keycloakService.getUser(id).getUsername();
    }
}