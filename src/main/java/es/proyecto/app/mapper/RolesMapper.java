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

    @Mapping(source = "id_role", target = "id_role")
    @Mapping(source = "role_name", target = "role_name")
    RoleResponse toApiDomain(RolesEntity source);

    // Método para convertir una lista de RolesEntity a RoleListResponse
    default RoleListResponse toApiDomainList(List<RolesEntity> source) {
        RoleListResponse roleListResponse = new RoleListResponse();
        List<RoleResponse> roleResponses = toApiDomain(source);  // Convierte la lista de RolesEntity a RoleResponse
        roleListResponse.addAll(roleResponses);  // Agrega todos los RoleResponse a RoleListResponse
        return roleListResponse;
    }

    // Mapeo de una lista de RolesEntity a una lista de RoleResponse
    List<RoleResponse> toApiDomain(List<RolesEntity> source);

    @Mapping(source = "id_role", target = "id_role")
    @Mapping(source = "role_name", target = "role_name")
    RolesEntity toEntity(RoleResponse source);
}
