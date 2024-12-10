package es.proyecto.app.mapper;

import es.proyecto.app.entity.OrderDetailsEntity;
import es.swagger.codegen.models.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {

    OrderDetailsMapper INSTANCE = Mappers.getMapper(OrderDetailsMapper.class);

    // Mapea de OrderDetailsEntity a OrderDetail (API Model)
    @Mapping(source = "idDetail", target = "idDetail")
    @Mapping(source = "order.idOrder", target = "idOrder")
    @Mapping(source = "product.idProduct", target = "idProduct")
    @Mapping(source = "quantity", target = "quantity")
    OrderDetails toApiDomain(OrderDetailsEntity source);

    // Mapea de OrderDetail a OrderDetailsEntity
    @Mapping(source = "idDetail", target = "idDetail")
    @Mapping(source = "idOrder", target = "order.idOrder")
    @Mapping(source = "idProduct", target = "product.idProduct")
    @Mapping(source = "quantity", target = "quantity")
    OrderDetailsEntity toEntity(OrderDetails source);

    // Mapea listas de OrderDetailsEntity a listas de OrderDetail
    List<OrderDetails> toApiDomainList(List<OrderDetailsEntity> source);

    // Mapea listas de OrderDetail a listas de OrderDetailsEntity
    List<OrderDetailsEntity> toEntityList(List<OrderDetails> source);
}