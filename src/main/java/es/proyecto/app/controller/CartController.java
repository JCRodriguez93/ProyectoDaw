package es.proyecto.app.controller;

import es.proyecto.app.service.CartService;
import es.swagger.codegen.api.CartApi;
import es.swagger.codegen.models.CartProductResponse;
import es.swagger.codegen.models.ManageCartRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CartController implements CartApi {

    @Autowired
    private CartService cartService;
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);


    public ResponseEntity<Void> manageCart(Integer userId, ManageCartRequest body) {
        logger.info("manageCart called with userId: {} and action: {}", userId, body.getAction());

        if (body.getAction() == null) {
            logger.error("Action is null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        boolean result;
        switch (body.getAction()) {
            case ADD:
                logger.info("Attempting to add product to cart: productId={}, quantity={}", body.getProductId(), body.getQuantity());
                result = cartService.addProductToCart(userId, body.getProductId(), body.getQuantity());
                if (result) {
                    logger.info("Product added successfully");
                    return ResponseEntity.ok().build();
                } else {
                    logger.error("Failed to add product to cart");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            case MODIFY:
                logger.info("Attempting to modify product in cart: productId={}, quantity={}", body.getProductId(), body.getQuantity());
                result = cartService.modifyProductInCart(userId, body.getProductId(), body.getQuantity());
                if (result) {
                    logger.info("Product modified successfully");
                    return ResponseEntity.ok().build();
                } else {
                    logger.error("Failed to modify product in cart");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            case REMOVE:
                logger.info("Attempting to remove product from cart: productId={}, quantity={}", body.getProductId(), body.getQuantity());
                result = cartService.removeProductFromCart(userId, body.getProductId(), body.getQuantity());
                if (result) {
                    logger.info("Product removed successfully");
                    return ResponseEntity.ok().build();
                } else {
                    logger.error("Failed to remove product from cart");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            default:
                logger.error("Invalid action: {}", body.getAction());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

    }


    @Override
    public ResponseEntity<List<CartProductResponse>> viewCart(Integer userId) {
        List<CartProductResponse> cartProducts = cartService.viewCart(userId);
        return ResponseEntity.ok(cartProducts);
    }
}