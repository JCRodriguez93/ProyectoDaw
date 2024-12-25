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

    // Mapea de ProductEntity a Product
    @Mapping(source = "idProduct", target = "idProduct")
    @Mapping(source = "idSubcategory.idSubcategory", target = "idSubcategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "imageUrl", target = "imageUrl")

    Products toApiDomain(ProductsEntity source);
    List<Products> toApiDomain(List<ProductsEntity> source);

    // Mapea de Product a ProductEntity
    @Mapping(source = "idProduct", target = "idProduct")
    @Mapping(source = "idSubcategory", target = "idSubcategory.idSubcategory")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "imageUrl", target = "imageUrl")
    ProductsEntity toEntity(Products source);


}
