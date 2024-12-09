package es.proyecto.app.mapper;

import es.proyecto.app.entity.CategoryEntity;
import es.proyecto.app.entity.SubcategoryEntity;
import es.swagger.codegen.models.Category;
import es.swagger.codegen.models.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    // Mapea de CategoryEntity a Category (API Model)
    @Mapping(source = "idCategory", target = "idCategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "subcategories", target = "subcategories")
    Category toApiDomain(CategoryEntity source);

    // Mapea SubcategoryEntity a Subcategory
    @Mapping(source = "idSubcategory", target = "idSubcategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Subcategory toApiDomain(SubcategoryEntity source);
    List<Category> toApiDomainList(List<CategoryEntity> source);

    // Mapea de Category a CategoryEntity
    @Mapping(source = "idCategory", target = "idCategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "subcategories", ignore = true)
    CategoryEntity toEntity(Category source);
}