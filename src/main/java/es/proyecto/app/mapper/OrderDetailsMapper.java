package es.proyecto.app.mapper;

import es.proyecto.app.entity.OrderDetailsEntity;
import es.swagger.codegen.models.OrderDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {

    OrderDetailsMapper INSTANCE = Mappers.getMapper(OrderDetailsMapper.class);

    @Mapping(source = "id_detail", target = "detailId")
    @Mapping(source = "id_order", target = "orderId")
    @Mapping(source = "id_product", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "total_price", target = "total")
    @Mapping(source = "product.name", target = "product_name")
    OrderDetailResponse toApiDomain(OrderDetailsEntity source);

    List<OrderDetailResponse> toApiDomain(List<OrderDetailsEntity> source);

    @Mapping(source = "detailId", target = "id_detail")
    @Mapping(source = "orderId", target = "id_order")
    @Mapping(source = "productId", target = "id_product")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "total", target = "total_price")
    OrderDetailsEntity toEntity(OrderDetailResponse source);
}
