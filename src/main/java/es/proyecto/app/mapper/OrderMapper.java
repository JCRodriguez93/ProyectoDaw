package es.proyecto.app.mapper;

import es.proyecto.app.entity.OrdersEntity;
import es.swagger.codegen.models.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // Instancia de MapStruct
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // Mapea de OrdersEntity a Order (API Model)
    @Mapping(source = "idOrder", target = "idOrder")
    @Mapping(source = "user.idUser", target = "idUser")
    @Mapping(target = "date", ignore = true)
    @Mapping(source = "status", target = "status")
    Orders toApiDomain(OrdersEntity source);

    // Mapea de Order a OrdersEntity
    @Mapping(source = "idOrder", target = "idOrder")
    @Mapping(source = "idUser", target = "user.idUser")
    @Mapping(target = "date", ignore = true)
    @Mapping(source = "status", target = "status")
    OrdersEntity toEntity(Orders source);

    // Convierte listas
    List<Orders> toApiDomainList(List<OrdersEntity> source);
    List<OrdersEntity> toEntityList(List<Orders> source);


}