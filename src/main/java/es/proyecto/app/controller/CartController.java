package es.proyecto.app.controller;

import es.proyecto.app.entity.OrderProductEntity;
import es.proyecto.app.entity.OrdersEntity;
import es.proyecto.app.service.OrdersService;
import es.proyecto.app.mapper.ProductMapper;
import es.swagger.codegen.api.CartApi;
import es.swagger.codegen.models.AddProductRequest;
import es.swagger.codegen.models.OrderProductResponse;
import es.swagger.codegen.models.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;


/*TODO: ver por qué postman no me muestra el carrito perteneciente a un usuario, siempre sale vacio.
en la base de datos tampoco se añade el registro*/

@RestController
public class CartController implements CartApi {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseEntity<Void> addProductToCart(AddProductRequest body) {
        ordersService.addProductToOrder(body.getOrderId(), body.getProductId(), body.getQuantity());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeProductFromCart(Integer orderId, Integer productId) {
        ordersService.removeProductFromOrder(orderId, productId);
        return ResponseEntity.ok().build();
    }

    @Override public ResponseEntity<Void> updateProductQuantity(UpdateProductRequest body) {
        ordersService.updateProductQuantity(body.getOrderId(), body.getProductId(), body.getQuantity());
        return ResponseEntity.ok().build(); }

    @Override
    public ResponseEntity<List<OrderProductResponse>> viewCart(Integer userId) {
        OrdersEntity currentOrder = ordersService.findCurrentOrderByUserId(userId);
        List<OrderProductEntity> orderProducts = ordersService.findByOrderId(currentOrder.getIdOrder());
        List<OrderProductResponse> response = orderProducts.stream()
                .map(op -> {
                    OrderProductResponse orderProductResponse = new OrderProductResponse();
                    orderProductResponse.setProduct(productMapper.toApiDomain(op.getIdProduct()));
                    orderProductResponse.setQuantity(op.getQuantity());
                    return orderProductResponse;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
