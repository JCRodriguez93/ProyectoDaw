package es.proyecto.app.mapper;

import es.proyecto.app.entity.OrdersEntity;
import es.swagger.codegen.models.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

    OrdersMapper INSTANCE = Mappers.getMapper(OrdersMapper.class);

    @Mapping(source = "id_order", target = "orderId")
    @Mapping(source = "id_user", target = "userId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "date", target = "order_date")
    @Mapping(source = "total_price", target = "totalAmount")
    OrderResponse toApiDomain(OrdersEntity source);

    List<OrderResponse> toApiDomain(List<OrdersEntity> source);

    @Mapping(source = "orderId", target = "id_order")
    @Mapping(source = "userId", target = "id_user")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "order_date", target = "date")
    @Mapping(source = "totalAmount", target = "total_price")
    OrdersEntity toEntity(OrderResponse source);
}
