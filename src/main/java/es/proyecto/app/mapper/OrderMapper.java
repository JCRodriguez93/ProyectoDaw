package es.proyecto.app.mapper;

import es.proyecto.app.entity.OrdersEntity;
import es.swagger.codegen.models.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.threeten.bp.OffsetDateTime;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // Mapea de OrderEntity a Order
    @Mapping(source = "idOrder", target = "idOrder")
    @Mapping(source = "user.idUser", target = "idUser")
    @Mapping(source = "totalQuantity", target = "totalQuantity")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "orderStatus", target = "orderStatus")
    Orders toApiDomain(OrdersEntity source);
    List<Orders> toApiDomain(List<OrdersEntity> source);

    // Mapea de Order a OrderEntity
    @Mapping(source = "idOrder", target = "idOrder")
    @Mapping(source = "idUser", target = "user.idUser")
    @Mapping(source = "totalQuantity", target = "totalQuantity")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "orderStatus", target = "orderStatus")
    OrdersEntity toEntity(Orders source);

}
