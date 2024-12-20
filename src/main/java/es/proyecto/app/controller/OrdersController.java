package es.proyecto.app.controller;


import es.proyecto.app.error.CategoryException;
import es.proyecto.app.error.OrderException;
import es.proyecto.app.error.UsersException;
import es.proyecto.app.service.OrdersService;
import es.swagger.codegen.api.OrdersApi;
import es.swagger.codegen.models.Orders;
import es.swagger.codegen.models.OrdersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
            // log.error("Null body provided");
            throw new OrderException("Null body provided");
        }

        //TODO: hacer comprobación que el ID no sea el mismo
        //TODO: al crear la orden, mandar excepción si el status es algo diferente a lo aceptado.


        ordersService.createOrder(body);
        log.info("order created successfully: {}", body.getIdOrder());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteOrder(Integer idOrder) {
        if (!isValidId(String.valueOf(idOrder))) {
            log.error("Invalid category ID format: {}", idOrder);
            throw OrderException.INVALID_ORDER_ID_EXCEPTION;
        }

        Orders deleteOrder = ordersService.getOrderById(idOrder);

        if (deleteOrder == null) {
            log.error("Employee with id {} not found", idOrder);
            throw OrderException.NO_ORDER_FOUND_EXCEPTION;
        }

        try {
            ordersService.deleteOrder(idOrder);
            log.info("Order with id {} deleted successfully", idOrder);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting order with id {}: {}", idOrder, e.getMessage());
            throw new OrderException("Error deleting order with id " + idOrder);
        }
    }

    @Override
    public ResponseEntity<Orders> getOrderById(Integer idOrder) {
        try {
            if (!isValidId(String.valueOf(idOrder))) {
                log.error("Invalid Category ID format: {}", idOrder);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Orders order = ordersService.getOrderById(idOrder);

            if (order == null) {
                log.info("order with id {} not found", idOrder);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("order with id {} retrieved successfully", idOrder);
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error retrieving order with id {}: {}", idOrder, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<OrdersResponse> getOrders() {
        try {
            List<Orders> ordersList = ordersService.getAllOrders();


            if (ordersList.isEmpty()) {
                //log.error("No categories found");
                throw OrderException.NO_ORDER_FOUND_EXCEPTION;
            }


            OrdersResponse response = new OrdersResponse();
            response.setOrders(ordersList);
           // log.info("Successfully fetched all orders");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CategoryException e) {
          //  log.error("Error fetching all orders: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Orders> updateOrder(Integer idOrder, Orders body) {
        try {
            if (body == null) {
                log.error("Null body provided");
                throw OrderException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdOrder() == null) {
                log.error("order ID is required for updating");
                throw OrderException.MISSING_ORDER_ID_EXCEPTION;
            }

            Orders existingOrder = ordersService.getOrderById(idOrder);
            if (existingOrder == null) {
                log.error("No order found with ID {}", idOrder);
                throw OrderException.NO_ORDER_FOUND_EXCEPTION;
            }

            //TODO: al actualizar la orden, mandar excepción si el status es algo diferente a lo aceptado.
            HttpStatus status = ordersService.updateOrder(idOrder, body);
            log.info("order with id {} updated successfully", idOrder);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            log.error("Invalid ID format: {}", idOrder);
            throw OrderException.INVALID_ORDER_ID_EXCEPTION;
        } catch (Exception e) {
            log.error("Error updating category with id {}: {}", idOrder, e.getMessage());
            throw new OrderException("Error updating category");
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
