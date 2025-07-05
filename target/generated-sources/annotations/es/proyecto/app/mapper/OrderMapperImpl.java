package es.proyecto.app.mapper;

import es.proyecto.app.entity.OrdersEntity;
import es.proyecto.app.entity.UsersEntity;
import es.swagger.codegen.models.Orders;
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
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Orders toApiDomain(OrdersEntity source) {
        if ( source == null ) {
            return null;
        }

        Orders orders = new Orders();

        orders.setIdOrder( source.getIdOrder() );
        orders.setIdUser( sourceUserIdUser( source ) );
        orders.setTotalQuantity( source.getTotalQuantity() );
        if ( source.getTotalPrice() != null ) {
            orders.setTotalPrice( source.getTotalPrice().doubleValue() );
        }
        orders.setDate( source.getDate() );
        orders.setOrderStatus( source.getOrderStatus() );

        return orders;
    }

    @Override
    public List<Orders> toApiDomain(List<OrdersEntity> source) {
        if ( source == null ) {
            return null;
        }

        List<Orders> list = new ArrayList<Orders>( source.size() );
        for ( OrdersEntity ordersEntity : source ) {
            list.add( toApiDomain( ordersEntity ) );
        }

        return list;
    }

    @Override
    public OrdersEntity toEntity(Orders source) {
        if ( source == null ) {
            return null;
        }

        OrdersEntity.OrdersEntityBuilder ordersEntity = OrdersEntity.builder();

        ordersEntity.user( ordersToUsersEntity( source ) );
        ordersEntity.idOrder( source.getIdOrder() );
        ordersEntity.totalQuantity( source.getTotalQuantity() );
        if ( source.getTotalPrice() != null ) {
            ordersEntity.totalPrice( BigDecimal.valueOf( source.getTotalPrice() ) );
        }
        ordersEntity.date( source.getDate() );
        ordersEntity.orderStatus( source.getOrderStatus() );

        return ordersEntity.build();
    }

    private Integer sourceUserIdUser(OrdersEntity ordersEntity) {
        if ( ordersEntity == null ) {
            return null;
        }
        UsersEntity user = ordersEntity.getUser();
        if ( user == null ) {
            return null;
        }
        Integer idUser = user.getIdUser();
        if ( idUser == null ) {
            return null;
        }
        return idUser;
    }

    protected UsersEntity ordersToUsersEntity(Orders orders) {
        if ( orders == null ) {
            return null;
        }

        UsersEntity.UsersEntityBuilder usersEntity = UsersEntity.builder();

        usersEntity.idUser( orders.getIdUser() );

        return usersEntity.build();
    }
}
