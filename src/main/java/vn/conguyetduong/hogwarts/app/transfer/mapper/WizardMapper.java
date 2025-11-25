package vn.conguyetduong.hogwarts.app.transfer.mapper;

import org.mapstruct.*;
import vn.conguyetduong.hogwarts.app.transfer.dto.wizart.*;
import vn.conguyetduong.hogwarts.business.service.external.KeycloakService;
import vn.conguyetduong.hogwarts.infra.model.Wizard;
import vn.conguyetduong.hogwarts.infra.model.WizardImage;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WizardMapper {

    Wizard toWizard(RegisterWizardRequest request);

    WizardImage toWizardImage(ImageRequest request);

    @AfterMapping
    default void setWizardInImage(@MappingTarget Wizard wizard) {
        if (wizard.getImages() != null) {
            wizard.getImages().forEach(img -> img.setWizard(wizard));
        }
    }

    // ----------

    @Mapping(target = "createdBy", source = "wizard.createdBy", qualifiedByName = "mapIdToUsername")
    @Mapping(target = "updatedBy", source = "wizard.updatedBy", qualifiedByName = "mapIdToUsername")
    WizardResponse toWizardResponse(Wizard wizard, @Context KeycloakService keycloakService);

    ImageResponse toImageResponse(WizardImage image);

    @Named("mapIdToUsername")
    default String mapCreatedBy(UUID id, @Context KeycloakService keycloakService) {
        return keycloakService.getUser(id).getUsername();
    }

    // ------
    List<ShortWizardResponse> toShortWizardResponses(List<Wizard> wizards);

    ShortWizardResponse toShortWizardResponse(Wizard wizard);

}

