package es.proyecto.app.service;

import es.proyecto.app.entity.OrdersEntity;
import es.proyecto.app.mapper.OrderMapper;
import es.proyecto.app.repository.OrdersRepository;
import es.swagger.codegen.models.Orders;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de Roles para la gestión de roles en el sistema.
 */
@Validated
@Transactional
@Service
public class OrdersService {

    private final OrderMapper mapper = OrderMapper.INSTANCE;

    @Autowired
    private OrdersRepository repository;

    public List<Orders> getAllOrders() {

        return mapper.toApiDomain(repository.findAll());

    }
    public HttpStatus createOrder(Orders idOrder) {
        OrdersEntity entity = mapper.toEntity(idOrder);
        repository.save(entity);
        return HttpStatus.CREATED;
    }
    public Orders getOrderById(Integer idOrder) {
        Optional<OrdersEntity> optionalOrdersEntity = repository.findById(idOrder);
        return optionalOrdersEntity.map(mapper::toApiDomain).orElse(null);
    }
    public boolean deleteOrder(Integer idOrder) {
        if (repository.existsById(idOrder)) {
            repository.deleteById(idOrder);
            return true;
        } else {
            return false;
        }
    }
    public HttpStatus updateOrder(Integer idOrder, Orders order) {

        Optional<OrdersEntity> existingOrder = repository.findById(idOrder);
        if(existingOrder.isEmpty()){
            return HttpStatus.NOT_FOUND;
        }
        order.setIdOrder(idOrder);
        repository.save(mapper.toEntity(order));
        return HttpStatus.OK;
    }






}


