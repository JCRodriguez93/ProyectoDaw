package es.proyecto.app.mapper;

import es.proyecto.app.entity.ProductsEntity;
import es.swagger.codegen.models.ProductResponse;
import es.swagger.codegen.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductsMapper {

    ProductsMapper INSTANCE = Mappers.getMapper(ProductsMapper.class);

    // Mapeo para convertir un ProductEntity a ProductResponse
    @Mapping(source = "id_product", target = "product.id")
    @Mapping(source = "name", target = "product.name")
    @Mapping(source = "description", target = "product.description")
    @Mapping(source = "price", target = "product.price")
    @Mapping(source = "stock", target = "product.stock")
    @Mapping(source = "subcategory.name", target = "product.subcategory")
    @Mapping(source = "category.name", target = "product.category")
    default ProductResponse toApiDomain(ProductsEntity source) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setSuccess(true);  // Indicador de éxito por defecto
        productResponse.setMessage("Producto recuperado con éxito");
        productResponse.setProduct(toApiDomainProduct(source));  // Convierte el producto y lo establece
        return productResponse;
    }

    // Método adicional para mapear ProductsEntity a Product (usado dentro de ProductResponse)
    @Mapping(source = "id_product", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "stock", target = "stock")
    @Mapping(source = "subcategory.name", target = "subcategory")
    @Mapping(source = "category.name", target = "category")
    Product toApiDomainProduct(ProductsEntity source);

    // Mapeo para convertir una lista de ProductsEntity a una lista de ProductResponse
    List<ProductResponse> toApiDomain(List<ProductsEntity> source);

    // Mapeo de ProductResponse a ProductsEntity
    @Mapping(source = "product.id", target = "id_product")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.description", target = "description")
    @Mapping(source = "product.price", target = "price")
    @Mapping(source = "product.stock", target = "stock")
    @Mapping(source = "product.subcategory", target = "subcategory.name")
    @Mapping(source = "product.category", target = "category.name")
    ProductsEntity toEntity(ProductResponse source);
}
