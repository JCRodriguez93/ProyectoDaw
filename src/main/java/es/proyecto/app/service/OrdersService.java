package es.proyecto.app.service;

import es.proyecto.app.entity.OrderProductEntity;
import es.proyecto.app.entity.OrdersEntity;
import es.proyecto.app.entity.ProductsEntity;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.mapper.OrderMapper;
import es.proyecto.app.repository.OrderProductRepository;
import es.proyecto.app.repository.OrdersRepository;
import es.proyecto.app.repository.ProductsRepository;
import es.swagger.codegen.models.OrderStatus;
import es.swagger.codegen.models.Orders;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@Validated
@Transactional
@Service
public class OrdersService {

    private final OrderMapper mapper = OrderMapper.INSTANCE;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderProductRepository orderProductsRepository;

    @Autowired
    private ProductsRepository productsRepository;

    public List<Orders> getAllOrders() {
        return mapper.toApiDomain(ordersRepository.findAll());
    }

    public HttpStatus createOrder(Orders idOrder) {
        OrdersEntity entity = mapper.toEntity(idOrder);
        ordersRepository.save(entity);
        return HttpStatus.CREATED;
    }

    public Orders getOrderById(Integer idOrder) {
        Optional<OrdersEntity> optionalOrdersEntity = ordersRepository.findById(idOrder);
        return optionalOrdersEntity.map(mapper::toApiDomain).orElse(null);
    }

    public boolean deleteOrder(Integer idOrder) {
        if (ordersRepository.existsById(idOrder)) {
            ordersRepository.deleteById(idOrder);
            return true;
        } else {
            return false;
        }
    }

    public HttpStatus updateOrder(Integer idOrder, Orders order) {
        Optional<OrdersEntity> existingOrder = ordersRepository.findById(idOrder);
        if(existingOrder.isEmpty()){
            return HttpStatus.NOT_FOUND;
        }
        order.setIdOrder(idOrder);
        ordersRepository.save(mapper.toEntity(order));
        return HttpStatus.OK;
    }





    // Métodos adicionales
    public OrdersEntity findCurrentOrderByUserId(int userId) {
        return ordersRepository.findByUser_IdUserAndOrderStatus(userId,
                OrderStatus.PENDIENTE) .orElseGet(() -> {
                    OrdersEntity newOrder = new OrdersEntity();
                    UsersEntity user = new UsersEntity(); user.setIdUser(userId);
                    newOrder.setUser(user); newOrder.setOrderStatus(OrderStatus.PENDIENTE);
                    newOrder.setTotalQuantity(0); newOrder.setTotalPrice(BigDecimal.ZERO);
                    return ordersRepository.save(newOrder); }); }

    public List<OrderProductEntity> findByOrderId(Integer orderId) {
        return orderProductsRepository.findByOrderId(orderId); }



    public void addProductToOrder(int orderId, int productId, int quantity) {
        Logger logger = Logger.getLogger(OrdersService.class.getName());

        Optional<ProductsEntity> product = productsRepository.findById(productId);
        if (product.isPresent()) {
            OrdersEntity order = ordersRepository.findById(orderId).orElseThrow();
            OrderProductEntity orderProduct = new OrderProductEntity();
            orderProduct.setIdOrder(order);
            orderProduct.setIdProduct(product.get());
            orderProduct.setQuantity(quantity);

            // Guardar OrderProductEntity en la base de datos
            orderProductsRepository.save(orderProduct);

            // Verificación adicional
            Optional<OrderProductEntity> savedOrderProduct = orderProductsRepository.findById(orderProduct.getIdOrderProduct());
            if (savedOrderProduct.isPresent()) {
                logger.info("Producto guardado en la base de datos: " + savedOrderProduct.get());
            } else {
                logger.severe("Error al guardar el producto en la base de datos");
            }

            // Actualizar la cantidad total y el precio total del pedido
            order.setTotalQuantity(order.getTotalQuantity() + quantity);
            order.setTotalPrice(order.getTotalPrice().add(product.get().getPrice().multiply(BigDecimal.valueOf(quantity))));
            ordersRepository.save(order);

            // Log de depuración
            logger.info("Pedido actualizado: " + order);
        } else {
            throw new IllegalArgumentException("Producto no encontrado");
        }
    }





    public void updateProductQuantity(Integer orderId, Integer productId, Integer quantity) {
        Optional<OrderProductEntity> orderProduct = orderProductsRepository.findByOrderIdAndProductId(orderId, productId);
        if (orderProduct.isPresent()) {
            OrderProductEntity op = orderProduct.get();
            op.setQuantity(quantity); orderProductsRepository.save(op);
        } else
        { throw new IllegalArgumentException("Producto no encontrado en el pedido");
        } }




    public void removeProductFromOrder(Integer orderId, Integer productId) {
        Optional<OrderProductEntity> orderProduct = orderProductsRepository.findByOrderIdAndProductId(orderId, productId);
        orderProduct.ifPresent(orderProductsRepository::delete);
    }
}

