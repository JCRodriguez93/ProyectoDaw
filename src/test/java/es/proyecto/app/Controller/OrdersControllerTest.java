package es.proyecto.app.Controller;

import es.proyecto.app.controller.OrdersController;
import es.proyecto.app.error.OrderException;
import es.proyecto.app.service.OrdersService;
import es.swagger.codegen.models.OrderStatus;
import es.swagger.codegen.models.Orders;
import es.swagger.codegen.models.OrdersResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrdersControllerTest {

    @Mock
    private OrdersService ordersService;

    @InjectMocks
    private OrdersController ordersController;

    private Orders validOrder;
    private Orders invalidOrder;

    @BeforeEach
    public void setUp() {
        // Inicializamos las órdenes
        validOrder = new Orders();
        validOrder.setIdOrder(1);
        validOrder.setIdUser(1);
        validOrder.setTotalQuantity(10);
        validOrder.setTotalPrice(999.90);
        validOrder.setDate(LocalDateTime.now());
        validOrder.setOrderStatus(OrderStatus.PENDIENTE);

        invalidOrder = new Orders();
        invalidOrder.setIdOrder(2);
        invalidOrder.setIdUser(null);  // Usuario nulo para la prueba de error
        invalidOrder.setTotalQuantity(0);
        invalidOrder.setTotalPrice(0.0);
        invalidOrder.setDate(LocalDateTime.now());
    }

    @Test
    @DisplayName("Crear orden con datos válidos")
    public void createOrderWithValidDataAndThenReturnsCreated() {
        // Simulamos el comportamiento del servicio de creación de órdenes
        when(ordersService.createOrder(validOrder)).thenReturn(HttpStatus.CREATED);

        // Llamamos al método del controlador
        ResponseEntity<Orders> response = ordersController.createOrder(validOrder);

        // Verificamos que la respuesta sea correcta (creación exitosa)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificamos que el servicio fue llamado con los argumentos esperados
        verify(ordersService, times(1)).createOrder(validOrder);
    }
    @Test
    @DisplayName("Crear orden con datos no válidos")
    public void createOrderWithInvalidDataAndThenReturnsNullBodyException() {
        // Simulamos que el servicio lanza la excepción cuando el body es null
        when(ordersService.createOrder(null)).thenThrow(OrderException.NULL_BODY_EXCEPTION);

        // Llamamos al método del controlador con un body null
        try {
            ordersController.createOrder(null);
            fail("Se esperaba una OrderException");
        } catch (OrderException e) {
            // Verificamos que la excepción lanzada sea la correcta
            assertEquals(OrderException.NULL_BODY_EXCEPTION.getMessage(), e.getMessage());
        }
    }
    @Test
    @DisplayName("Crear orden con usuario nulo")
    public void createOrderWithNullUserAndThenReturnsBadRequest() {
        // Comprobamos que se lanza la excepción esperada
        OrderException exception = assertThrows(OrderException.class, () -> {
            ordersController.createOrder(invalidOrder);
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("Invalid order status provided", exception.getMessage());
    }

    @Test
    @DisplayName("Obtener orden por ID")
    public void getOrderByIdAndThenReturnOk() {
        // Simulamos que la orden existe
        when(ordersService.getOrderById(1)).thenReturn(validOrder);

        // Llamamos al método del controlador para obtener la orden por ID
        ResponseEntity<Orders> response = ordersController.getOrderById(1);

        // Verificamos que se retorna un status OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verificamos que la orden retornada sea la esperada
        assertEquals(validOrder, response.getBody());
    }

    @Test
    @DisplayName("Actualizar orden con datos válidos")
    public void updateOrderWithValidDataAndThenReturnOk() {
        // Orden existente en la base de datos
        when(ordersService.getOrderById(1)).thenReturn(validOrder);

        // Creamos una orden actualizada con nuevos datos
        Orders updatedOrder = new Orders();
        updatedOrder.setIdOrder(1);
        updatedOrder.setIdUser(2);
        updatedOrder.setTotalQuantity(5);
        updatedOrder.setTotalPrice(499.95);
        updatedOrder.setDate(LocalDateTime.now());
        updatedOrder.setOrderStatus(OrderStatus.CONFIRMADO);

        // Simulamos que la orden se actualiza correctamente
        when(ordersService.updateOrder(updatedOrder.getIdOrder(), updatedOrder)).thenReturn(HttpStatus.OK);

        // Llamamos al método del controlador para actualizar la orden
        ResponseEntity<Orders> response = ordersController.updateOrder(updatedOrder.getIdOrder(), updatedOrder);

        // Verificamos que se retorne un status OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que el servicio fue llamado con los argumentos esperados
        verify(ordersService, times(1)).updateOrder(updatedOrder.getIdOrder(), updatedOrder);
    }

    @Test
    @DisplayName("Eliminar orden")
    public void deleteOrderAndThenReturnNoContent() {
        // Simulamos el comportamiento del servicio de eliminación de órdenes
        when(ordersService.getOrderById(1)).thenReturn(validOrder);

        // Llamamos al método del controlador para eliminar la orden
        ResponseEntity<Void> response = ordersController.deleteOrder(1);

        // Verificamos que se retorne un status NO_CONTENT (204)
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verificamos que el servicio fue llamado con los argumentos esperados
        verify(ordersService, times(1)).deleteOrder(1);
    }

    @Test
    @DisplayName("Obtener todas las órdenes")
    public void getOrdersAndThenReturnOk() {
        // Simulamos la lista de órdenes
        List<Orders> ordersList = Arrays.asList(validOrder);
        when(ordersService.getAllOrders()).thenReturn(ordersList);

        // Llamamos al método del controlador para obtener todas las órdenes
        ResponseEntity<OrdersResponse> response = ordersController.getOrders();

        // Verificamos que se retorna un status OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verificamos que la lista de órdenes retornada sea la esperada
        assertEquals(ordersList, response.getBody().getOrders());
    }
}
