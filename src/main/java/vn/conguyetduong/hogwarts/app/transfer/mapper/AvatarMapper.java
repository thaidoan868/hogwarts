package vn.conguyetduong.hogwarts.app.transfer.mapper;

import org.mapstruct.Mapper;
import vn.conguyetduong.hogwarts.app.transfer.dto.profile.AvatarResponse;
import vn.conguyetduong.hogwarts.infra.model.profile.Avatar;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvatarMapper {
    AvatarResponse toAvatarResponse(Avatar avatar);
    List<AvatarResponse> toAvatarResponseList(List<Avatar> avatarList);
}
