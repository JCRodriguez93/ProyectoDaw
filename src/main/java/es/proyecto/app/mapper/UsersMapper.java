package es.proyecto.app.mapper;

import es.proyecto.app.entity.UsersEntity;
import es.swagger.codegen.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper para convertir entre {@link UsersEntity} y {@link User}.
 * <p>
 * Utiliza MapStruct para mapear automáticamente los campos entre la entidad de base de datos
 * y el modelo de la API.
 * </p>
 *
 * Incluye métodos para convertir objetos individuales y listas completas de usuarios.
 */
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
