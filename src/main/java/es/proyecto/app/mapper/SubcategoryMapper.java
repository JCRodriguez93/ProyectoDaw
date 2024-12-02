package es.proyecto.app.mapper;

import es.proyecto.app.entity.SubcategoryEntity;
import es.swagger.codegen.models.SubcategoryResponse;
import es.swagger.codegen.models.SubcategoryListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper {

    SubcategoryMapper INSTANCE = Mappers.getMapper(SubcategoryMapper.class);

    @Mapping(source = "id_subcategory", target = "idSubcategory")
    @Mapping(source = "id_category", target = "idCategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    SubcategoryResponse toApiDomain(SubcategoryEntity source);

    // Método para convertir una lista de SubcategoryEntity a SubcategoryListResponse
    default SubcategoryListResponse toApiDomainList(List<SubcategoryEntity> source) {
        SubcategoryListResponse subcategoryListResponse = new SubcategoryListResponse();
        List<SubcategoryResponse> subcategoryResponses = toApiDomain(source);  // Convierte la lista de SubcategoryEntity a SubcategoryResponse
        subcategoryListResponse.addAll(subcategoryResponses);  // Agrega todos los SubcategoryResponse a SubcategoryListResponse
        return subcategoryListResponse;
    }

    // Mapeo de una lista de SubcategoryEntity a una lista de SubcategoryResponse
    List<SubcategoryResponse> toApiDomain(List<SubcategoryEntity> source);

    @Mapping(source = "idSubcategory", target = "id_subcategory")
    @Mapping(source = "idCategory", target = "id_category")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    SubcategoryEntity toEntity(SubcategoryResponse source);
}
