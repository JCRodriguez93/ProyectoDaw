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

    // Mapea de UserEntity a User
    @Mapping(source = "idUser", target = "idUser")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "userSurname", target = "userSurname")
    @Mapping(source = "birthDate", target = "userBirth")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "roleId.idRole", target = "roleId")
    User toApiDomain(UsersEntity source);
    List<User> toApiDomain(List<UsersEntity> source);

    // Mapea de User a UserEntity
    @Mapping(source = "idUser", target = "idUser")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "userSurname", target = "userSurname")
    @Mapping(source = "userBirth", target = "birthDate")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "roleId", target = "roleId.idRole")
    UsersEntity toEntity(User source);



}
