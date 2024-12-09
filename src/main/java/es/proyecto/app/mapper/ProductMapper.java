package es.proyecto.app.mapper;

import es.proyecto.app.entity.ProductsEntity;
import es.proyecto.app.entity.SubcategoryEntity;
import es.swagger.codegen.models.Products;
import es.swagger.codegen.models.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Mapea de ProductsEntity a Product (API Model)
    @Mapping(source = "idProduct", target = "idProduct")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
   // @Mapping(source = "subcategory", target = "idSubcategory")
    Products toApiDomain(ProductsEntity source);

    // Mapea de SubcategoryEntity a Subcategory (anidado en Product)
    @Mapping(source = "idSubcategory", target = "idSubcategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Subcategory toApiDomain(SubcategoryEntity source);

    // Lista de ProductsEntity a lista de Product
    List<Products> toApiDomainList(List<ProductsEntity> source);

    // Mapea de Product a ProductsEntity
    @Mapping(source = "idProduct", target = "idProduct")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
   // @Mapping(source = "subcategory", target = "subcategory")
    ProductsEntity toEntity(Products source);
}
