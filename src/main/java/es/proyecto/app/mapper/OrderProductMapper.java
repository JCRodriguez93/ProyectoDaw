package es.proyecto.app.mapper;

import es.proyecto.app.entity.OrderProductEntity;
import es.swagger.codegen.models.OrderProducts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    OrderProductMapper INSTANCE = Mappers.getMapper(OrderProductMapper.class);

    // Mapea de OrderProductEntity a OrderProduct
    @Mapping(source = "idOrderProduct", target = "idOrderProduct")
    @Mapping(source = "idOrder.idOrder", target = "idOrder")
    @Mapping(source = "idProduct.idProduct", target = "idProduct")
    @Mapping(source = "quantity", target = "quantity")
    OrderProducts toApiDomain(OrderProductEntity source);
    List<OrderProducts> toApiDomain(List<OrderProductEntity> source);

    // Mapea de OrderProduct a OrderProductEntity
    @Mapping(source = "idOrderProduct", target = "idOrderProduct")
    @Mapping(source = "idOrder", target = "idOrder.idOrder")
    @Mapping(source = "idProduct", target = "idProduct.idProduct")
    @Mapping(source = "quantity", target = "quantity")
    OrderProductEntity toEntity(OrderProducts source);
}
