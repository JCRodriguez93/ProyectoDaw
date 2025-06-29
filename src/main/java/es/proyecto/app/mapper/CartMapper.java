package es.proyecto.app.mapper;

import es.proyecto.app.entity.CartEntity;
import es.swagger.codegen.models.CartProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

/**
 * Mapper para convertir entre {@link CartEntity} y {@link CartProductResponse}.
 * Utiliza MapStruct para realizar el mapeo automático entre las entidades
 * de la base de datos y los modelos usados en la API.
 */
@Mapper(componentModel = "spring")
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    // Mapea de CartEntity a CartProductResponse
    @Mapping(source = "product.idProduct", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "product.price", target = "price")
    @Mapping(target = "totalPrice", expression = "java(cartEntity.getProduct().getPrice().multiply(new BigDecimal(cartEntity.getQuantity())).toString())")
    CartProductResponse toApiDomain(CartEntity cartEntity);

    List<CartProductResponse> toApiDomain(List<CartEntity> cartEntities);

    // Mapea de CartProductResponse a CartEntity (si es necesario)
    @Mapping(source = "productId", target = "product.idProduct")
    @Mapping(target = "product.name", ignore = true) // No se mapea en esta dirección
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "product.price")
    @Mapping(target = "idCart", ignore = true) // Ignorar este mapeo ya que no está en CartProductResponse
    @Mapping(target = "user.idUser", ignore = true) // Ignorar este mapeo ya que no está en CartProductResponse
    CartEntity toEntity(CartProductResponse cartProductResponse);

    // Método auxiliar para convertir BigDecimal a String (sin @Named)
    default String bigDecimalToString(BigDecimal bigDecimal) {
        return bigDecimal != null ? bigDecimal.toString() : null;
    }
}
