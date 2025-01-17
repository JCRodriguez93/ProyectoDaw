package es.proyecto.app.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import es.proyecto.app.controller.CartController;
import es.proyecto.app.service.CartService;
import es.swagger.codegen.models.ManageCartRequest;
import org.junit.jupiter.api.BeforeEach;
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
    public void testAddProductToCart_Success() {
        ManageCartRequest request = new ManageCartRequest();
        request.setAction(ManageCartRequest.ActionEnum.ADD);
        request.setProductId(2);
        request.setQuantity(33);

        when(cartService.addProductToCart(3, 2, 33)).thenReturn(true);

        ResponseEntity<Void> response = cartController.manageCart(3, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddProductToCart_Failure() {
        ManageCartRequest request = new ManageCartRequest();
        request.setAction(ManageCartRequest.ActionEnum.ADD);
        request.setProductId(2);
        request.setQuantity(33);

        when(cartService.addProductToCart(3, 2, 33)).thenReturn(false);

        ResponseEntity<Void> response = cartController.manageCart(3, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // Similar tests for MODIFY and REMOVE actions...

    @Test
    public void testManageCart_InvalidAction() {
        ManageCartRequest request = new ManageCartRequest();
        request.setAction(null); // or any invalid action

        ResponseEntity<Void> response = cartController.manageCart(3, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
