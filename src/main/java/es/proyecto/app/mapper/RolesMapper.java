package es.proyecto.app.mapper;

import es.proyecto.app.entity.RolesEntity;
import es.swagger.codegen.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolesMapper {

    RolesMapper INSTANCE = Mappers.getMapper(RolesMapper.class);

    // Mapea de RolesEntity a RoleResponse
    @Mapping(source = "idRole", target = "idRole")
    @Mapping(source = "roleName", target = "roleName")
    Role toApiDomain(RolesEntity source);
    List<Role> toApiDomain(List<RolesEntity> source);


    // Mapea de RoleResponse a RolesEntity
    @Mapping(source = "idRole", target = "idRole")
    @Mapping(source = "roleName", target = "roleName")
    RolesEntity toEntity(Role source );
}
