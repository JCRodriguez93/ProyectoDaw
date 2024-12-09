package es.proyecto.app.mapper;

import es.proyecto.app.entity.SubcategoryEntity;
import es.swagger.codegen.models.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper {

    SubcategoryMapper INSTANCE = Mappers.getMapper(SubcategoryMapper.class);

    /**
     * Convierte SubcategoryEntity a Subcategory.
     */
    @Mapping(source = "idSubcategory", target = "idSubcategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Subcategory toApiDomain(SubcategoryEntity source);

    /**
     * Convierte una lista de SubcategoryEntity a una lista de Subcategory.
     */
    List<Subcategory> toApiDomain(List<SubcategoryEntity> source);
}
