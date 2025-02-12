package es.proyecto.app.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import es.proyecto.app.controller.CartController;
import es.proyecto.app.service.CartService;
import es.swagger.codegen.models.ManageCartRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Añadir producto al carrito ")
    public void addProductToCartAndThenReturnOK() {
        // Crear una solicitud de gestión del carrito
        ManageCartRequest request = new ManageCartRequest();
        // Establecer la acción como ADD (añadir producto)
        request.setAction(ManageCartRequest.ActionEnum.ADD);
        // Establecer el ID del producto a 2
        request.setProductId(2);
        // Establecer la cantidad del producto a 33
        request.setQuantity(33);

        // Simular la llamada al servicio de carrito para añadir el producto
        when(cartService.addProductToCart(3, 2, 33)).thenReturn(true);

        // Llamar al controlador para gestionar el carrito con la solicitud creada
        ResponseEntity<Void> response = cartController.manageCart(3, request);

        // Verificar que la respuesta del controlador es OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    @DisplayName("intentar añadir producto al carrito pero obtener bad request")
    public void attemptToAddProductToCartButThrowBadRequest() {
        // Crear una solicitud de gestión del carrito
        ManageCartRequest request = new ManageCartRequest();
        // Establecer la acción como ADD (añadir producto)
        request.setAction(ManageCartRequest.ActionEnum.ADD);
        // Establecer el ID del producto a 2
        request.setProductId(2);
        // Establecer la cantidad del producto a 33
        request.setQuantity(33);

        // Simular la llamada al servicio de carrito para añadir el producto y devolver false (fallo en la operación)
        when(cartService.addProductToCart(3, 2, 33)).thenReturn(false);

        // Llamar al controlador para gestionar el carrito con la solicitud creada
        ResponseEntity<Void> response = cartController.manageCart(3, request);

        // Verificar que la respuesta del controlador es BAD_REQUEST (400)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    @DisplayName("intentar acción invalida en el manejo del carrito")
    public void manageCartButInvalidActionReturnBadRequest() {
        // Crear una solicitud de gestión del carrito
        ManageCartRequest request = new ManageCartRequest();
        // Establecer la acción como null o cualquier acción no válida
        request.setAction(null); // o cualquier acción no válida

        // Llamar al controlador para gestionar el carrito con la solicitud creada
        ResponseEntity<Void> response = cartController.manageCart(3, request);

        // Verificar que la respuesta del controlador es BAD_REQUEST (400)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    @DisplayName("modificar producto del carrito")
    public void modifyProductInCartThenReturnOK() {
        // Crear una solicitud de gestión del carrito
        ManageCartRequest request = new ManageCartRequest();
        // Establecer la acción como MODIFY (modificar producto)
        request.setAction(ManageCartRequest.ActionEnum.MODIFY);
        // Establecer el ID del producto a 2
        request.setProductId(2);
        // Establecer la nueva cantidad del producto a 10
        request.setQuantity(10);

        // Simular la llamada al servicio de carrito para modificar el producto
        when(cartService.modifyProductInCart(3, 2, 10)).thenReturn(true);

        // Llamar al controlador para gestionar el carrito con la solicitud creada
        ResponseEntity<Void> response = cartController.manageCart(3, request);

        // Verificar que la respuesta del controlador es OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("intentar modificar producto del carrito pero obtener bad request")
    public void attemptToModifyProductInCartButThrowBadRequest() {
        // Crear una solicitud de gestión del carrito
        ManageCartRequest request = new ManageCartRequest();
        // Establecer la acción como MODIFY (modificar producto)
        request.setAction(ManageCartRequest.ActionEnum.MODIFY);
        // Establecer el ID del producto a 2
        request.setProductId(2);
        // Establecer la nueva cantidad del producto a 10
        request.setQuantity(10);

        // Simular que el producto no está en el carrito del usuario 3
        when(cartService.modifyProductInCart(3, 2, 10)).thenReturn(false);

        // Llamar al controlador para gestionar el carrito con la solicitud creada
        ResponseEntity<Void> response = cartController.manageCart(3, request);

        // Verificar que la respuesta del controlador es NOT_FOUND (404), ya que el producto no está en el carrito
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    @DisplayName("borrar producto del carrito y obtener not found")
    public void removeProductFromCartThenReturnNotFound() {
        // Crear una solicitud de gestión del carrito
        ManageCartRequest request = new ManageCartRequest();
        // Establecer la acción como REMOVE (eliminar producto)
        request.setAction(ManageCartRequest.ActionEnum.REMOVE);
        // Establecer el ID del producto a 2
        request.setProductId(2);

        // Simular que el producto no está en el carrito
        when(cartService.removeProductFromCart(3, 2, 1)).thenReturn(false);

        // Llamar al controlador para gestionar el carrito con la solicitud creada
        ResponseEntity<Void> response = cartController.manageCart(3, request);

        // Verificar que la respuesta del controlador es NOT_FOUND (404)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    @DisplayName("intentar borrar producto del carrito inexistente ")
    public void attemptToRemoveProductFromCartThenReturnNotFound() {
        // Crear una solicitud de gestión del carrito
        ManageCartRequest request = new ManageCartRequest();
        // Establecer la acción como REMOVE (eliminar producto)
        request.setAction(ManageCartRequest.ActionEnum.REMOVE);
        // Establecer el ID del producto a 2
        request.setProductId(2);

        // Simular que el producto no está en el carrito del usuario 3
        when(cartService.removeProductFromCart(3, 2, 1)).thenReturn(false);

        // Llamar al controlador para gestionar el carrito con la solicitud creada
        ResponseEntity<Void> response = cartController.manageCart(3, request);

        // Verificar que la respuesta del controlador es NOT_FOUND (404)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



}
