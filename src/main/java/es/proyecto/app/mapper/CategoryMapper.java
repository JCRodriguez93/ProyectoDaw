package es.proyecto.app.mapper;

import es.proyecto.app.entity.CategoryEntity;
import es.swagger.codegen.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


/**
 * Mapper para convertir entre {@link CategoryEntity} y {@link Category}.
 * <p>
 * Utiliza MapStruct para mapear automáticamente los campos entre la entidad de base de datos
 * y el modelo de la API. También delega el mapeo de subcategorías al {@link SubcategoryMapper}.
 * </p>
 *
 * Esta interfaz define métodos para convertir tanto objetos individuales como listas de categorías.
 */
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
