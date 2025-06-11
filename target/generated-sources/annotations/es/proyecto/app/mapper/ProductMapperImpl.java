package es.proyecto.app.mapper;

import es.proyecto.app.entity.ProductsEntity;
import es.proyecto.app.entity.SubcategoryEntity;
import es.swagger.codegen.models.Products;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-11T21:40:53+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Products toApiDomain(ProductsEntity source) {
        if ( source == null ) {
            return null;
        }

        Products products = new Products();

        products.setIdProduct( source.getIdProduct() );
        products.setIdSubcategory( sourceIdSubcategoryIdSubcategory( source ) );
        products.setName( source.getName() );
        products.setDescription( source.getDescription() );
        if ( source.getPrice() != null ) {
            products.setPrice( source.getPrice().doubleValue() );
        }
        products.setImageUrl( source.getImageUrl() );

        return products;
    }

    @Override
    public List<Products> toApiDomain(List<ProductsEntity> source) {
        if ( source == null ) {
            return null;
        }

        List<Products> list = new ArrayList<Products>( source.size() );
        for ( ProductsEntity productsEntity : source ) {
            list.add( toApiDomain( productsEntity ) );
        }

        return list;
    }

    @Override
    public ProductsEntity toEntity(Products source) {
        if ( source == null ) {
            return null;
        }

        ProductsEntity.ProductsEntityBuilder productsEntity = ProductsEntity.builder();

        productsEntity.idSubcategory( productsToSubcategoryEntity( source ) );
        if ( source.getIdProduct() != null ) {
            productsEntity.idProduct( source.getIdProduct() );
        }
        productsEntity.name( source.getName() );
        productsEntity.description( source.getDescription() );
        if ( source.getPrice() != null ) {
            productsEntity.price( BigDecimal.valueOf( source.getPrice() ) );
        }
        productsEntity.imageUrl( source.getImageUrl() );

        return productsEntity.build();
    }

    private Integer sourceIdSubcategoryIdSubcategory(ProductsEntity productsEntity) {
        if ( productsEntity == null ) {
            return null;
        }
        SubcategoryEntity idSubcategory = productsEntity.getIdSubcategory();
        if ( idSubcategory == null ) {
            return null;
        }
        Integer idSubcategory1 = idSubcategory.getIdSubcategory();
        if ( idSubcategory1 == null ) {
            return null;
        }
        return idSubcategory1;
    }

    protected SubcategoryEntity productsToSubcategoryEntity(Products products) {
        if ( products == null ) {
            return null;
        }

        SubcategoryEntity.SubcategoryEntityBuilder subcategoryEntity = SubcategoryEntity.builder();

        subcategoryEntity.idSubcategory( products.getIdSubcategory() );

        return subcategoryEntity.build();
    }
}
