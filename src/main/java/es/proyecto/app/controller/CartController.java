package es.proyecto.app.controller;

import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.service.CartService;
import es.proyecto.app.service.UsersService;
import es.swagger.codegen.api.CartApi;
import es.swagger.codegen.models.CartProductResponse;
import es.swagger.codegen.models.ManageCartRequest;
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
     * Obtiene el ID del usuario autenticado actualmente.
     * <p>
     * Este método recupera el `Authentication` del contexto de seguridad de Spring,
     * extrae el email desde el token JWT (mediante `auth.getName()`), y luego busca
     * al usuario correspondiente en la base de datos para obtener su ID.
     *
     * @return el ID del usuario autenticado como {@link Integer},
     *         o {@code null} si el usuario no está autenticado
     *         o no se encuentra en la base de datos.
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




    /**
     * Gestiona las acciones relacionadas con el carrito de un usuario autenticado.
     * <p>
     * Según el valor del campo {@code action} en la petición {@link ManageCartRequest},
     * se realizará una de las siguientes operaciones sobre el carrito:
     * <ul>
     *     <li><b>ADD</b>: Añade un producto con la cantidad especificada.</li>
     *     <li><b>MODIFY</b>: Modifica la cantidad de un producto ya existente.</li>
     *     <li><b>REMOVE</b>: Elimina la cantidad indicada de un producto del carrito.</li>
     * </ul>
     * Si el usuario no está autenticado o si los datos enviados no son válidos, se responderá con el
     * estado HTTP correspondiente.
     *
     * @param body objeto {@link ManageCartRequest} que contiene la acción, el ID del producto y la cantidad.
     * @return una {@link ResponseEntity} con el código de estado:
     *         <ul>
     *             <li>{@code 200 OK} si la operación fue exitosa.</li>
     *             <li>{@code 400 Bad Request} si hay errores de validación o acción inválida.</li>
     *             <li>{@code 401 Unauthorized} si el usuario no está autenticado.</li>
     *             <li>{@code 404 Not Found} si se intenta modificar o eliminar un producto que no existe en el carrito.</li>
     *         </ul>
     */
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

    /**
     * Recupera los productos del carrito del usuario autenticado.
     * <p>
     * Este método obtiene el ID del usuario autenticado y solicita al servicio del carrito
     * la lista de productos asociados a dicho usuario. Si el usuario no está autenticado
     * o si el carrito está vacío, se devuelve el estado HTTP correspondiente.
     *
     * @return una {@link ResponseEntity} que contiene:
     * <ul>
     *     <li>Una lista de {@link CartProductResponse} con los productos del carrito y {@code 200 OK} si hay contenido.</li>
     *     <li>{@code 401 Unauthorized} si no hay un usuario autenticado.</li>
     *     <li>{@code 404 Not Found} si el carrito está vacío o no existe.</li>
     * </ul>
     */
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
