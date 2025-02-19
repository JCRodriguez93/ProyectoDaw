package es.proyecto.app.controller;

import es.proyecto.app.error.OrderException;
import es.proyecto.app.service.OrdersService;
import es.swagger.codegen.api.OrdersApi;
import es.swagger.codegen.models.OrderStatus;
import es.swagger.codegen.models.Orders;
import es.swagger.codegen.models.OrdersResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Override
    public ResponseEntity<Orders> createOrder(Orders body) {
        if (body == null) {
            logger.error("Null body provided");
            throw OrderException.NULL_BODY_EXCEPTION;
        }

        // Comprobación de que el ID no sea el mismo
        if (body.getIdOrder() != null && ordersService.getOrderById(body.getIdOrder()) != null) {
            logger.error("Order with ID {} already exists", body.getIdOrder());
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Retorna 409 Conflict
        }

        // Comprobación de que el status sea válido
        List<OrderStatus> validStatuses = Arrays.asList(OrderStatus.PENDIENTE,
        OrderStatus.PAGADO, OrderStatus.CANCELADO, OrderStatus.CONFIRMADO);
        if (body.getOrderStatus() == null || !validStatuses.contains(body.getOrderStatus())) {
            logger.error("Invalid order status provided: {}", body.getOrderStatus());
            throw OrderException.INVALID_ORDER_STATUS_EXCEPTION;
        }

        ordersService.createOrder(body);
        logger.info("Order created successfully: {}", body.getIdOrder());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @Override
    public ResponseEntity<Void> deleteOrder(Integer idOrder) {
        if (!isValidId(String.valueOf(idOrder))) {
            logger.error("Invalid order ID format: {}", idOrder);
            throw OrderException.INVALID_ORDER_ID_EXCEPTION;
        }

        Orders deleteOrder = ordersService.getOrderById(idOrder);

        if (deleteOrder == null) {
            logger.error("Order with id {} not found", idOrder);
            throw OrderException.NO_ORDER_FOUND_EXCEPTION;
        }

        try {
            ordersService.deleteOrder(idOrder);
            logger.info("Order with id {} deleted successfully", idOrder);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting order with id {}. Message: {}", idOrder, e.getMessage());
            throw new OrderException("Error deleting order with id " + idOrder);
        }
    }

    @Override
    public ResponseEntity<Orders> getOrderById(Integer idOrder) {
        try {
            if (!isValidId(String.valueOf(idOrder))) {
                logger.error("Cannot obtain order with ID: {}", idOrder);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Orders order = ordersService.getOrderById(idOrder);

            if (order == null) {
                logger.info("Order with id {} not found", idOrder);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                logger.info("Order with id {} retrieved successfully", idOrder);
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error retrieving order with id {}. Message: {}", idOrder, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<OrdersResponse> getOrders() {
        try {
            List<Orders> ordersList = ordersService.getAllOrders();

            if (ordersList.isEmpty()) {
                logger.error("No orders found");
                throw OrderException.NO_ORDER_FOUND_EXCEPTION;
            }

            OrdersResponse response = new OrdersResponse();
            response.setOrders(ordersList);
            logger.info("Successfully fetched all orders");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (OrderException e) {
            logger.error("Error fetching all orders: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Orders> updateOrder(Integer idOrder, Orders body) {
        try {
            if (body == null) {
                logger.error("Cannot update order. Null body provided");
                throw OrderException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdOrder() == null) {
                logger.error("Order ID is required for updating");
                throw OrderException.MISSING_ORDER_ID_EXCEPTION;
            }

            Orders existingOrder = ordersService.getOrderById(idOrder);
            if (existingOrder == null) {
                logger.error("No order found with ID {}", idOrder);
                throw OrderException.NO_ORDER_FOUND_EXCEPTION;
            }

            // Comprobación de que el status sea válido para la actualización
            List<OrderStatus> updatableStatuses = Arrays.asList(OrderStatus.PENDIENTE, OrderStatus.CONFIRMADO); // Estados que permiten la actualización
            if (!updatableStatuses.contains(existingOrder.getOrderStatus())) {
                logger.error("Order with ID {} cannot be updated because its status is {}", idOrder, existingOrder.getOrderStatus());
                throw OrderException.INVALID_UPDATE_DUE_CURRENT_STATUS_EXCEPTION;
            }

            HttpStatus status = ordersService.updateOrder(idOrder, body);
            logger.info("Order with id {} updated successfully", idOrder);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            logger.error("Invalid ID format: {}", idOrder);
            throw OrderException.INVALID_ORDER_ID_EXCEPTION;
        } catch (Exception e) {
            logger.error("Error updating order with id {}. Message: {}", idOrder, e.getMessage());
            throw new OrderException("Error updating order");
        }
    }


    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
