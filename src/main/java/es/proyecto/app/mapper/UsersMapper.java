package es.proyecto.app.mapper;

import es.proyecto.app.entity.UsersEntity;
import es.swagger.codegen.models.UserResponse;
import es.swagger.codegen.models.UserListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    @Mapping(source = "id_user", target = "id_user")
    @Mapping(source = "user_name", target = "user_name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role.id_role", target = "role.id_role")
    @Mapping(source = "role.role_name", target = "role.role_name")
    UserResponse toApiDomain(UsersEntity source);

    List<UserResponse> toApiDomain(List<UsersEntity> source);

    default UserListResponse toUserListResponse(List<UsersEntity> entities) {
        UserListResponse response = new UserListResponse();
        response.addAll(toApiDomain(entities)); // Utiliza `addAll` directamente porque `UserListResponse` extiende `ArrayList`
        return response;
    }

    @Mapping(source = "id_user", target = "id_user")
    @Mapping(source = "user_name", target = "user_name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role_id", target = "role_id")
    UsersEntity toEntity(UserResponse source);
}
