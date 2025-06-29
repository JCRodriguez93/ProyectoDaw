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
 * Controlador REST que gestiona las operaciones relacionadas con los pedidos del sistema.
 *
 * <p>Esta clase implementa la interfaz {@link OrdersApi}, generada automáticamente a partir del
 * contrato Swagger. Expone endpoints para crear, consultar, actualizar y eliminar pedidos.
 * También proporciona validaciones específicas para los estados permitidos y la estructura
 * del cuerpo de la petición.</p>
 *
 * <p>El controlador actúa como intermediario entre la capa de presentación y el servicio
 * {@link OrdersService}, delegando la lógica de negocio a dicha capa y manejando la construcción
 * de respuestas HTTP adecuadas según el resultado de las operaciones.</p>
 *
 * <p>El sistema utiliza logs detallados (mediante {@link Logger}) para registrar eventos relevantes
 * como errores, validaciones fallidas o éxitos en la gestión de pedidos.</p>
 *
 * <p>Las posibles excepciones se gestionan a través de la clase personalizada {@link OrderException},
 * que encapsula distintos tipos de errores relacionados con los pedidos.</p>
 *
 * @author Jorge
 */
@Slf4j
@RestController
public class OrdersController implements OrdersApi {

    @Autowired
    private OrdersService ordersService;

    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);

    /**
     * Crea un nuevo pedido en el sistema.
     * <p>
     * Este método valida el objeto {@link Orders} recibido antes de proceder con la creación:
     * <ul>
     *     <li>Verifica que el cuerpo de la petición no sea nulo.</li>
     *     <li>Comprueba que no exista ya un pedido con el mismo ID.</li>
     *     <li>Valida que el estado del pedido sea uno de los valores permitidos:
     *         {@code PENDIENTE}, {@code PAGADO}, {@code CANCELADO} o {@code CONFIRMADO}.</li>
     * </ul>
     * Si las validaciones son correctas, se delega la creación al servicio correspondiente.
     *
     * @param body el objeto {@link Orders} que representa el nuevo pedido a registrar.
     * @return una {@link ResponseEntity} con:
     * <ul>
     *     <li>{@code 201 Created} si el pedido se creó correctamente.</li>
     *     <li>{@code 409 Conflict} si ya existe un pedido con el mismo ID.</li>
     * </ul>
     * @throws OrderException si el cuerpo es nulo o el estado del pedido es inválido.
     */

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


    /**
     * Elimina un pedido identificado por su ID.
     * <p>
     * Primero valida que el ID proporcionado sea válido. Luego intenta obtener el pedido
     * correspondiente; si no se encuentra, lanza una excepción específica. Si el pedido existe,
     * intenta eliminarlo, registrando tanto el éxito como cualquier error que ocurra.
     *
     * @param idOrder el identificador del pedido a eliminar.
     * @return un {@link ResponseEntity} con código HTTP 204 (NO_CONTENT) si la eliminación fue exitosa.
     * @throws OrderException si el ID es inválido, el pedido no existe o ocurre un error durante la eliminación.
     */
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

    /**
     * Obtiene un pedido por su identificador.
     * <p>
     * Valida el formato del ID recibido y luego intenta recuperar el pedido asociado.
     * Retorna los estados HTTP adecuados según el resultado:
     * <ul>
     *     <li>{@code 200 OK} con el pedido si se encuentra correctamente.</li>
     *     <li>{@code 400 BAD_REQUEST} si el ID es inválido.</li>
     *     <li>{@code 404 NOT_FOUND} si no existe ningún pedido con ese ID.</li>
     *     <li>{@code 500 INTERNAL_SERVER_ERROR} si ocurre un error inesperado durante la operación.</li>
     * </ul>
     *
     * @param idOrder el identificador del pedido a buscar.
     * @return un {@link ResponseEntity} que contiene el pedido encontrado o el estado HTTP correspondiente.
     */
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

    /**
     * Obtiene la lista completa de pedidos.
     * <p>
     * Intenta recuperar todos los pedidos disponibles. Si no se encuentra ninguno,
     * lanza una excepción específica. En caso de éxito, devuelve la lista envuelta
     * en un objeto {@link OrdersResponse} con un estado HTTP 200 (OK).
     * Si ocurre un error, devuelve un estado HTTP 500 (INTERNAL_SERVER_ERROR).
     *
     * @return un {@link ResponseEntity} que contiene un {@link OrdersResponse} con la lista
     *         de pedidos o un código de error HTTP.
     */
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

    /**
     * Actualiza un pedido existente con los datos proporcionados.
     * <p>
     * Valida que el cuerpo de la petición no sea nulo y que el pedido exista.
     * Además, comprueba que el estado del pedido permita su actualización
     * (solo los estados PENDIENTE y CONFIRMADO permiten actualización).
     * Si alguna validación falla, se lanza una excepción específica.
     * En caso de éxito, se actualiza el pedido y se retorna el estado HTTP correspondiente.
     *
     * @param idOrder el identificador del pedido a actualizar.
     * @param body el objeto {@link Orders} con los datos actualizados del pedido.
     * @return un {@link ResponseEntity} con el estado HTTP de la operación.
     * @throws OrderException si el cuerpo es nulo, falta el ID del pedido,
     *                        el pedido no existe, el estado no permite actualización,
     *                        o se produce un error durante la actualización.
     */

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

    /**
     * Verifica si una cadena dada representa un identificador numérico válido.
     * <p>
     * Este método intenta convertir la cadena a un {@code Integer}. Si la conversión tiene éxito,
     * se considera un ID válido. Si lanza una excepción {@code NumberFormatException},
     * se considera inválido.
     *
     * @param id la cadena a validar como identificador numérico.
     * @return {@code true} si la cadena puede convertirse a {@code Integer},
     *         {@code false} en caso contrario.
     */
    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
