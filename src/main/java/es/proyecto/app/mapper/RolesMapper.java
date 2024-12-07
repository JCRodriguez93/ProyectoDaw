package es.proyecto.app.mapper;

import es.proyecto.app.entity.RolesEntity;
import es.swagger.codegen.models.RoleResponse;
import es.swagger.codegen.models.RoleListResponse;
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
    RoleResponse toApiDomain(RolesEntity source);

    RoleListResponse toApiDomainList(RolesEntity source);

    List<RoleListResponse> toApiDomainList(List<RolesEntity> source);

    // Mapea de RoleResponse a RolesEntity
    @Mapping(source = "idRole", target = "idRole")
    @Mapping(source = "roleName", target = "roleName")
    @Mapping(target = "users", ignore = true)
    RolesEntity toEntity(RoleResponse source );
}
