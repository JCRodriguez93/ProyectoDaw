package es.proyecto.app.controller;

import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.security.JwtTokenProvider;
import es.proyecto.app.service.CartService;
import es.proyecto.app.service.UsersService;
import es.swagger.codegen.api.CartApi;
import es.swagger.codegen.models.CartProductResponse;
import es.swagger.codegen.models.ManageCartRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CartController implements CartApi {

    @Autowired
    private  CartService cartService;
    @Autowired
    private  UsersService userService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);



    /**
     * Obtiene el userId del usuario autenticado extrayendo el username del SecurityContext.
     */
    private Integer getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            // auth.getName() devuelve el email, ya que así se genera el token
            String email = auth.getName();

            // Obtener el usuario por email
            UsersEntity user = userService.getUserByEmail(email);
            if (user != null) {
                return user.getIdUser(); // Asumiendo que el objeto User tiene getId() que retorna Integer
            } else {
                // No se encontró usuario con ese email
                return null;
            }
        }
        return null;
    }





    @Override
    public ResponseEntity<Void> manageCart(ManageCartRequest body) {
        Integer userId = getAuthenticatedUserId();
        if (userId == null) {
            logger.error("Unauthorized access: no authenticated user found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        logger.info("manageCart called with userId: {} and action: {}", userId, body.getAction());

        if (body.getAction() == null) {
            logger.error("Action is null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        boolean result;
        // Usamos el valor en minúsculas para comparar
        switch (body.getAction()) {
            case ADD:
                logger.info("Attempting to add product to cart: product_id={}, quantity={}", body.getProductId(), body.getQuantity());
                result = cartService.addProductToCart(userId, body.getProductId(), body.getQuantity());
                if (result) {
                    logger.info("Product added successfully");
                    return ResponseEntity.ok().build();
                } else {
                    logger.error("Failed to add product to cart");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            case MODIFY:
                logger.info("Attempting to modify product in cart: product_id={}, quantity={}", body.getProductId(), body.getQuantity());
                result = cartService.modifyProductInCart(userId, body.getProductId(), body.getQuantity());
                if (result) {
                    logger.info("Product modified successfully");
                    return ResponseEntity.ok().build();
                } else {
                    logger.error("Failed to modify product in cart");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            case REMOVE:
                logger.info("Attempting to remove product from cart: product_id={}, quantity={}", body.getProductId(), body.getQuantity());
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
    public ResponseEntity<List<CartProductResponse>> viewCart() {
        Integer userId = getAuthenticatedUserId();
        if (userId == null) {
            logger.error("Unauthorized access: no authenticated user found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<CartProductResponse> cartProducts = cartService.viewCart(userId);
        if (cartProducts == null || cartProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(cartProducts);
    }
}
