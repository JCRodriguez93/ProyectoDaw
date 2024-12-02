package es.proyecto.app.mapper;

import es.proyecto.app.entity.CategoryEntity;
import es.swagger.codegen.models.CategoryResponse;
import es.swagger.codegen.models.CategoryListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "id_category", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryResponse toApiDomain(CategoryEntity source);

    // Mapear una lista de CategoryEntity a CategoryListResponse
    default CategoryListResponse toApiDomainList(List<CategoryEntity> source) {
        CategoryListResponse response = new CategoryListResponse();
        response.addAll(toApiDomainListItems(source)); // Usar addAll para agregar los elementos a la lista
        return response;
    }

    // Esta función convierte la lista de CategoryEntity a una lista de CategoryResponse
    List<CategoryResponse> toApiDomainListItems(List<CategoryEntity> source);

    @Mapping(source = "id", target = "id_category")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    CategoryEntity toEntity(CategoryResponse source);
}
