package es.proyecto.app.mapper;

import es.proyecto.app.entity.OrderProductEntity;
import es.proyecto.app.entity.OrdersEntity;
import es.proyecto.app.entity.ProductsEntity;
import es.swagger.codegen.models.OrderProducts;
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
public class OrderProductMapperImpl implements OrderProductMapper {

    @Override
    public OrderProducts toApiDomain(OrderProductEntity source) {
        if ( source == null ) {
            return null;
        }

        OrderProducts orderProducts = new OrderProducts();

        orderProducts.setIdOrderProduct( source.getIdOrderProduct() );
        orderProducts.setIdOrder( sourceIdOrderIdOrder( source ) );
        orderProducts.setIdProduct( sourceIdProductIdProduct( source ) );
        orderProducts.setQuantity( source.getQuantity() );

        return orderProducts;
    }

    @Override
    public List<OrderProducts> toApiDomain(List<OrderProductEntity> source) {
        if ( source == null ) {
            return null;
        }

        List<OrderProducts> list = new ArrayList<OrderProducts>( source.size() );
        for ( OrderProductEntity orderProductEntity : source ) {
            list.add( toApiDomain( orderProductEntity ) );
        }

        return list;
    }

    @Override
    public OrderProductEntity toEntity(OrderProducts source) {
        if ( source == null ) {
            return null;
        }

        OrderProductEntity.OrderProductEntityBuilder orderProductEntity = OrderProductEntity.builder();

        orderProductEntity.idOrder( orderProductsToOrdersEntity( source ) );
        orderProductEntity.idProduct( orderProductsToProductsEntity( source ) );
        orderProductEntity.idOrderProduct( source.getIdOrderProduct() );
        orderProductEntity.quantity( source.getQuantity() );

        return orderProductEntity.build();
    }

    private Integer sourceIdOrderIdOrder(OrderProductEntity orderProductEntity) {
        if ( orderProductEntity == null ) {
            return null;
        }
        OrdersEntity idOrder = orderProductEntity.getIdOrder();
        if ( idOrder == null ) {
            return null;
        }
        Integer idOrder1 = idOrder.getIdOrder();
        if ( idOrder1 == null ) {
            return null;
        }
        return idOrder1;
    }

    private Integer sourceIdProductIdProduct(OrderProductEntity orderProductEntity) {
        if ( orderProductEntity == null ) {
            return null;
        }
        ProductsEntity idProduct = orderProductEntity.getIdProduct();
        if ( idProduct == null ) {
            return null;
        }
        int idProduct1 = idProduct.getIdProduct();
        return idProduct1;
    }

    protected OrdersEntity orderProductsToOrdersEntity(OrderProducts orderProducts) {
        if ( orderProducts == null ) {
            return null;
        }

        OrdersEntity.OrdersEntityBuilder ordersEntity = OrdersEntity.builder();

        ordersEntity.idOrder( orderProducts.getIdOrder() );

        return ordersEntity.build();
    }

    protected ProductsEntity orderProductsToProductsEntity(OrderProducts orderProducts) {
        if ( orderProducts == null ) {
            return null;
        }

        ProductsEntity.ProductsEntityBuilder productsEntity = ProductsEntity.builder();

        if ( orderProducts.getIdProduct() != null ) {
            productsEntity.idProduct( orderProducts.getIdProduct() );
        }

        return productsEntity.build();
    }
}
