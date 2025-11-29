package vn.conguyetduong.hogwarts.app.transfer.mapper;

import org.mapstruct.*;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.*;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.model.Wizard;
import vn.conguyetduong.hogwarts.infra.model.WizardImage;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface WizardMapper {
    // --- creation

    Wizard toWizard(RegisterWizardRequest request);

    WizardImage toWizardImage(ImageRequest request);

    List<WizardImage> toWizardImageList(List<ImageRequest> imageRequests);

    @AfterMapping
    default void setWizardInImage(@MappingTarget Wizard wizard) {
        if (wizard.getImages() != null) {
            wizard.getImages().forEach(img -> img.setWizard(wizard));
        }
    }

    // --- update
    @BeanMapping( nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "images", ignore = true)
    Wizard patchUpdateWizard(WizardPatchUpdateRequest request, @MappingTarget Wizard wizard);

    @Mapping(target = "images", ignore = true)
    Wizard putUpdate(RegisterWizardRequest request, @MappingTarget Wizard wizard);


    // --- detail response
    @Mapping(target = "createdBy", source = "wizard.createdBy", qualifiedByName = "mapIdToUsername")
    @Mapping(target = "updatedBy", source = "wizard.updatedBy", qualifiedByName = "mapIdToUsername")
    WizardResponse toWizardResponse(Wizard wizard, @Context KeycloakService keycloakService);

    ImageResponse toImageResponse(WizardImage image);

    @Named("mapIdToUsername")
    default String mapCreatedBy(UUID id, @Context KeycloakService keycloakService) {
        return keycloakService.getUser(id).getUsername();
    }

    // --- short response
    List<ShortWizardResponse> toShortWizardResponses(List<Wizard> wizards);

    ShortWizardResponse toShortWizardResponse(Wizard wizard);

}

