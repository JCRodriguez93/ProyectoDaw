package es.proyecto.app.mapper;

import es.proyecto.app.entity.CartEntity;
import es.proyecto.app.entity.ProductsEntity;
import es.proyecto.app.entity.UsersEntity;
import es.swagger.codegen.models.CartProductResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-05T17:35:05+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartProductResponse toApiDomain(CartEntity cartEntity) {
        if ( cartEntity == null ) {
            return null;
        }

        CartProductResponse cartProductResponse = new CartProductResponse();

        cartProductResponse.setProductId( cartEntityProductIdProduct( cartEntity ) );
        cartProductResponse.setProductName( cartEntityProductName( cartEntity ) );
        cartProductResponse.setQuantity( cartEntity.getQuantity() );
        cartProductResponse.setPrice( bigDecimalToString( cartEntityProductPrice( cartEntity ) ) );

        cartProductResponse.setTotalPrice( cartEntity.getProduct().getPrice().multiply(new BigDecimal(cartEntity.getQuantity())).toString() );

        return cartProductResponse;
    }

    @Override
    public List<CartProductResponse> toApiDomain(List<CartEntity> cartEntities) {
        if ( cartEntities == null ) {
            return null;
        }

        List<CartProductResponse> list = new ArrayList<CartProductResponse>( cartEntities.size() );
        for ( CartEntity cartEntity : cartEntities ) {
            list.add( toApiDomain( cartEntity ) );
        }

        return list;
    }

    @Override
    public CartEntity toEntity(CartProductResponse cartProductResponse) {
        if ( cartProductResponse == null ) {
            return null;
        }

        CartEntity.CartEntityBuilder cartEntity = CartEntity.builder();

        cartEntity.product( cartProductResponseToProductsEntity( cartProductResponse ) );
        cartEntity.quantity( cartProductResponse.getQuantity() );
        cartEntity.user( cartProductResponseToUsersEntity( cartProductResponse ) );

        return cartEntity.build();
    }

    private Integer cartEntityProductIdProduct(CartEntity cartEntity) {
        if ( cartEntity == null ) {
            return null;
        }
        ProductsEntity product = cartEntity.getProduct();
        if ( product == null ) {
            return null;
        }
        int idProduct = product.getIdProduct();
        return idProduct;
    }

    private String cartEntityProductName(CartEntity cartEntity) {
        if ( cartEntity == null ) {
            return null;
        }
        ProductsEntity product = cartEntity.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal cartEntityProductPrice(CartEntity cartEntity) {
        if ( cartEntity == null ) {
            return null;
        }
        ProductsEntity product = cartEntity.getProduct();
        if ( product == null ) {
            return null;
        }
        BigDecimal price = product.getPrice();
        if ( price == null ) {
            return null;
        }
        return price;
    }

    protected ProductsEntity cartProductResponseToProductsEntity(CartProductResponse cartProductResponse) {
        if ( cartProductResponse == null ) {
            return null;
        }

        ProductsEntity.ProductsEntityBuilder productsEntity = ProductsEntity.builder();

        if ( cartProductResponse.getProductId() != null ) {
            productsEntity.idProduct( cartProductResponse.getProductId() );
        }
        if ( cartProductResponse.getPrice() != null ) {
            productsEntity.price( new BigDecimal( cartProductResponse.getPrice() ) );
        }

        return productsEntity.build();
    }

    protected UsersEntity cartProductResponseToUsersEntity(CartProductResponse cartProductResponse) {
        if ( cartProductResponse == null ) {
            return null;
        }

        UsersEntity.UsersEntityBuilder usersEntity = UsersEntity.builder();

        return usersEntity.build();
    }
}
