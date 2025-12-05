package vn.conguyetduong.hogwarts.app.transfer.mapper;

import org.mapstruct.*;
import vn.conguyetduong.hogwarts.app.transfer.dto.profile.ProfileRequest;
import vn.conguyetduong.hogwarts.app.transfer.dto.profile.DetailedProfileResponse;
import vn.conguyetduong.hogwarts.app.transfer.dto.profile.PublicProfileResponse;
import vn.conguyetduong.hogwarts.infra.constant.Accessibility;
import vn.conguyetduong.hogwarts.infra.model.profile.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    DetailedProfileResponse toProfileResponse(Profile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Profile patchUpdate(ProfileRequest request, @MappingTarget Profile profile);

    PublicProfileResponse toPublicProfileResponse(Profile profile);

    @AfterMapping
    default void hidePrivateFields(Profile profile, @MappingTarget PublicProfileResponse response) {

        if (profile.getAddress() != null &&
                profile.getAddress().getAccessibility() == Accessibility.PRIVATE) {
            response.setAddress(null);
        }

        if (profile.getBirthDate() != null &&
                profile.getBirthDate().getAccessibility() == Accessibility.PRIVATE) {
            response.setBirthDate(null);
        }

        if (profile.getGender() != null &&
                profile.getGender().getAccessibility() == Accessibility.PRIVATE) {
            response.setGender(null);
        }

        if (profile.getPhoneNumber() != null &&
                profile.getPhoneNumber().getAccessibility() == Accessibility.PRIVATE) {
            response.setPhoneNumber(null);
        }
    }
}
