package es.proyecto.app.mapper;

import es.proyecto.app.entity.SubcategoryEntity;
import es.swagger.codegen.models.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import org.mapstruct.factory.Mappers;

/**
 * Mapper para convertir entre {@link SubcategoryEntity} y {@link Subcategory}.
 * <p>
 * Utiliza MapStruct para mapear automáticamente los campos entre la entidad de base de datos
 * y el modelo de la API.
 * </p>
 *
 * Proporciona métodos para convertir objetos individuales y listas completas de subcategorías.
 */
@Mapper(componentModel = "spring")
public interface SubcategoryMapper {

    SubcategoryMapper INSTANCE = Mappers.getMapper(SubcategoryMapper.class);

    @Mapping(source = "category.idCategory", target = "idCategory")
    @Mapping(source = "idSubcategory", target = "idSubcategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Subcategory toApiDomain(SubcategoryEntity source);

    List<Subcategory> toApiDomain(List<SubcategoryEntity> source);

    // Mapea de Subcategory a SubcategoryEntity
    @Mapping(source = "idCategory", target = "category.idCategory")
    @Mapping(source = "idSubcategory", target = "idSubcategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    SubcategoryEntity toEntity(Subcategory source);
}
