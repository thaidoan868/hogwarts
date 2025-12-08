package vn.conguyetduong.hogwarts.app.transfer.mapper;

import lombok.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.conguyetduong.hogwarts.app.transfer.dto.user.RegisterUserRequest;
import vn.conguyetduong.hogwarts.infra.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "email", source = "email", qualifiedByName = "toLowerCase")
    User toUser(RegisterUserRequest request);

    @Named("toLowerCase")
    default String toLowerCase(String value) {
        return value == null ? null : value.toLowerCase();
    }
}