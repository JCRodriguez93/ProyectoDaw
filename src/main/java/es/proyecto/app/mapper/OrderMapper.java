package es.proyecto.app.mapper;

import es.proyecto.app.entity.OrdersEntity;
import es.swagger.codegen.models.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.threeten.bp.OffsetDateTime;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Mapper para convertir entre {@link OrdersEntity} y {@link Orders}.
 * <p>
 * Utiliza MapStruct para mapear automáticamente los campos entre la entidad de base de datos
 * y el modelo de la API.
 * </p>
 *
 * Esta interfaz define métodos para convertir tanto objetos individuales como listas de pedidos.
 */
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
