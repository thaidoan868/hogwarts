package vn.conguyetduong.hogwarts.app.transfer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.conguyetduong.hogwarts.app.transfer.dto.user.RegisterUserRequest;
import vn.conguyetduong.hogwarts.infra.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterUserRequest request);
}