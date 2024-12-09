package es.proyecto.app.controller;


import es.proyecto.app.error.CategoryException;
import es.proyecto.app.error.OrderDetailsException;
import es.proyecto.app.error.OrderException;
import es.proyecto.app.service.OrderDetailsService;
import es.proyecto.app.service.OrdersService;
import es.swagger.codegen.api.OrderDetailsApi;
import es.swagger.codegen.api.OrdersApi;
import es.swagger.codegen.models.OrderDetails;
import es.swagger.codegen.models.OrderDetailsResponse;
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
public class OrderDetailsController implements OrderDetailsApi {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Override
    public ResponseEntity<OrderDetailsResponse> getOrderDetails() {
        try {
            List<OrderDetails> orderDetailsList = orderDetailsService.getAllOrders();


            if (orderDetailsList.isEmpty()) {
                //log.error("No categories found");
                throw OrderDetailsException.NO_ORDER_DETAILS_FOUND_EXCEPTION;
            }


            OrderDetailsResponse response = new OrderDetailsResponse();
            response.setOrderDetails(orderDetailsList);
          //  log.info("Successfully fetched all order details");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CategoryException e) {
         //   log.error("Error fetching all order details: {}", e.getMessage());
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
