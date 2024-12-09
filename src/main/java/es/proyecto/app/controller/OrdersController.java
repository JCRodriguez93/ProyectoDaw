package es.proyecto.app.controller;


import es.proyecto.app.error.CategoryException;
import es.proyecto.app.error.OrderException;
import es.proyecto.app.service.OrdersService;
import es.swagger.codegen.api.OrdersApi;
import es.swagger.codegen.models.CategoriesResponse;
import es.swagger.codegen.models.Orders;
import es.swagger.codegen.models.OrdersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RolesController es la implementación de RolesApi.
 */
@Slf4j
@RestController
public class OrdersController implements OrdersApi {

    @Autowired
    private OrdersService ordersService;

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


    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
