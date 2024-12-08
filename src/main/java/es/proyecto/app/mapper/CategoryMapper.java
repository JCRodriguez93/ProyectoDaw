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
    @Mapping(source = "subcategories", target = "subcategories") // Mapea las subcategorías
    Category toApiDomain(CategoryEntity source);
    List<Category> toApiDomain(List<CategoryEntity> source);



    // Mapea una lista de CategoryEntity a una lista de Category
    //List<Category> toApiDomain(List<CategoryEntity> source);

    // Mapea de Category a CategoryEntity
    @Mapping(source = "idCategory", target = "idCategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "subcategories", ignore = true) // Ignora las subcategorías al mapear hacia la entidad
    CategoryEntity toEntity(Category source);


}
