package es.proyecto.app.mapper;

import es.proyecto.app.entity.CategoryEntity;
import es.swagger.codegen.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SubcategoryMapper.class})
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "idCategory", target = "idCategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "subcategories", target = "subcategories")
    Category toApiDomain(CategoryEntity source);

    List<Category> toApiDomain(List<CategoryEntity> source);

    @Mapping(source = "idCategory", target = "idCategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "subcategories", target = "subcategories")
    CategoryEntity toEntity(Category source);
}
