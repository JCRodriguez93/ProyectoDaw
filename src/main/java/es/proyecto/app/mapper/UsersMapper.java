package es.proyecto.app.mapper;

import es.proyecto.app.entity.UsersEntity;
import es.swagger.codegen.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    // Mapea de UsersEntity a Users
    @Mapping(source = "idUser", target = "idUser")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "roleId", target = "roleId")
    //@Mapping(source = "role.roleName", target = "role")    //QUEDARÍA MAPEAR LOS ROLES
    User ztoApiDomain(UsersEntity source);
    List<User> toApiDomain(List<UsersEntity> source);

    // Mapea de Users a UsersEntity
    @Mapping(source = "idUser", target = "idUser")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "roleId", target = "roleId")
    @Mapping(target = "role", ignore = true)
    UsersEntity toEntity(User source);
}
