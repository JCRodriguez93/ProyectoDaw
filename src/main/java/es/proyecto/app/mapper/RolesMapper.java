package es.proyecto.app.mapper;

import es.proyecto.app.entity.RolesEntity;
import es.swagger.codegen.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper para convertir entre {@link RolesEntity} y {@link Role}.
 * <p>
 * Utiliza MapStruct para mapear automáticamente los campos entre la entidad de base de datos
 * y el modelo de la API.
 * </p>
 *
 * Proporciona métodos para convertir objetos individuales y listas completas de roles.
 */
@Mapper(componentModel = "spring")
public interface RolesMapper {

    RolesMapper INSTANCE = Mappers.getMapper(RolesMapper.class);

    // Mapea de RoleEntity a Role
    @Mapping(source = "idRole", target = "idRole")
    @Mapping(source = "roleName", target = "roleName")
    Role toApiDomain(RolesEntity source);
    List<Role> toApiDomain(List<RolesEntity> source);

    // Mapea de Role a RoleEntity
    @Mapping(source = "idRole", target = "idRole")
    @Mapping(source = "roleName", target = "roleName")
    RolesEntity toEntity(Role source);
}
