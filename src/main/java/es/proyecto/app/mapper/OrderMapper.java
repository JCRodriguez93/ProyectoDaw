package es.proyecto.app.mapper;

import es.proyecto.app.entity.OrdersEntity;
import es.swagger.codegen.models.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // Mapea de OrdersEntity a Order (API Model)
    @Mapping(source = "idOrder", target = "idOrder")
    @Mapping(source = "user.idUser", target = "idUser")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "status", target = "status")
    Orders toApiDomain(OrdersEntity source);

    // Mapea de Order a OrdersEntity
    @Mapping(source = "idOrder", target = "idOrder")
    @Mapping(source = "idUser", target = "user.idUser")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "status", target = "status")
    OrdersEntity toEntity(Orders source);

    // Mapea listas de OrdersEntity a listas de Order
    List<Orders> toApiDomainList(List<OrdersEntity> source);

    // Mapea listas de Order a listas de OrdersEntity
    List<OrdersEntity> toEntityList(List<Orders> source);
}
