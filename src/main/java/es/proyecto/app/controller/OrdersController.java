package es.proyecto.app.controller;

import es.proyecto.app.error.OrderException;
import es.proyecto.app.service.OrdersService;
import es.swagger.codegen.api.OrdersApi;
import es.swagger.codegen.models.OrderStatus;
import es.swagger.codegen.models.Orders;
import es.swagger.codegen.models.OrdersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

/**
 * OrdersController es la implementación de OrdersApi.
 */
@Slf4j
@RestController
public class OrdersController implements OrdersApi {

    @Autowired
    private OrdersService ordersService;

    @Override
    public ResponseEntity<Orders> createOrder(Orders body) {
        if (body == null) {
            log.error("Null body provided in createOrder");
            throw new OrderException("Null body provided");
        }

        // Comprobación de que el ID no sea el mismo
        if (body.getIdOrder() != null && ordersService.getOrderById(body.getIdOrder()) != null) {
            log.error("Order with ID {} already exists", body.getIdOrder());
            throw new OrderException("Order with the specified ID already exists");
        }

        // Comprobación de que el status sea válido
        List<OrderStatus> validStatuses = Arrays.asList(OrderStatus.PENDIENTE,
        OrderStatus.PAGADO, OrderStatus.CANCELADO, OrderStatus.CONFIRMADO);
        if (body.getOrderStatus() == null || !validStatuses.contains(body.getOrderStatus())) {
            log.error("Invalid order status provided: {}", body.getOrderStatus());
            throw new OrderException("Invalid order status provided");
        }

        ordersService.createOrder(body);
        log.info("Order created successfully: {}", body.getIdOrder());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @Override
    public ResponseEntity<Void> deleteOrder(Integer idOrder) {
        if (!isValidId(String.valueOf(idOrder))) {
            log.error("Invalid order ID format in deleteOrder: {}", idOrder);
            throw OrderException.INVALID_ORDER_ID_EXCEPTION;
        }

        Orders deleteOrder = ordersService.getOrderById(idOrder);

        if (deleteOrder == null) {
            log.error("Order with id {} not found in deleteOrder", idOrder);
            throw OrderException.NO_ORDER_FOUND_EXCEPTION;
        }

        try {
            ordersService.deleteOrder(idOrder);
            log.info("Order with id {} deleted successfully", idOrder);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting order with id {} in deleteOrder: {}", idOrder, e.getMessage());
            throw new OrderException("Error deleting order with id " + idOrder);
        }
    }

    @Override
    public ResponseEntity<Orders> getOrderById(Integer idOrder) {
        try {
            if (!isValidId(String.valueOf(idOrder))) {
                log.error("Invalid order ID format in getOrderById: {}", idOrder);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Orders order = ordersService.getOrderById(idOrder);

            if (order == null) {
                log.info("Order with id {} not found in getOrderById", idOrder);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("Order with id {} retrieved successfully in getOrderById", idOrder);
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error retrieving order with id {} in getOrderById: {}", idOrder, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<OrdersResponse> getOrders() {
        try {
            List<Orders> ordersList = ordersService.getAllOrders();

            if (ordersList.isEmpty()) {
                log.error("No orders found in getOrders");
                throw OrderException.NO_ORDER_FOUND_EXCEPTION;
            }

            OrdersResponse response = new OrdersResponse();
            response.setOrders(ordersList);
            log.info("Successfully fetched all orders in getOrders");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (OrderException e) {
            log.error("Error fetching all orders in getOrders: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Orders> updateOrder(Integer idOrder, Orders body) {
        try {
            if (body == null) {
                log.error("Null body provided in updateOrder");
                throw OrderException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdOrder() == null) {
                log.error("Order ID is required for updating in updateOrder");
                throw OrderException.MISSING_ORDER_ID_EXCEPTION;
            }

            Orders existingOrder = ordersService.getOrderById(idOrder);
            if (existingOrder == null) {
                log.error("No order found with ID {} in updateOrder", idOrder);
                throw OrderException.NO_ORDER_FOUND_EXCEPTION;
            }

            // Comprobación de que el status sea válido para la actualización
            List<OrderStatus> updatableStatuses = Arrays.asList(OrderStatus.PENDIENTE, OrderStatus.CONFIRMADO); // Estados que permiten la actualización
            if (!updatableStatuses.contains(existingOrder.getOrderStatus())) {
                log.error("Order with ID {} cannot be updated because its status is {}", idOrder, existingOrder.getOrderStatus());
                throw new OrderException("Order with the specified ID cannot be updated due to its current status");
            }

            HttpStatus status = ordersService.updateOrder(idOrder, body);
            log.info("Order with id {} updated successfully in updateOrder", idOrder);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            log.error("Invalid ID format in updateOrder: {}", idOrder);
            throw OrderException.INVALID_ORDER_ID_EXCEPTION;
        } catch (Exception e) {
            log.error("Error updating order with id {} in updateOrder: {}", idOrder, e.getMessage());
            throw new OrderException("Error updating order");
        }
    }


    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            log.error("Invalid ID format in isValidId: {}", id);
            return false;
        }
    }
}
