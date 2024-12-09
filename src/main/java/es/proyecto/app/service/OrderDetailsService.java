package es.proyecto.app.service;

import es.proyecto.app.mapper.OrderDetailsMapper;
import es.proyecto.app.mapper.OrderMapper;
import es.proyecto.app.repository.OrderDetailsRepository;
import es.proyecto.app.repository.OrdersRepository;
import es.swagger.codegen.models.OrderDetails;
import es.swagger.codegen.models.Orders;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Servicio de Roles para la gestión de roles en el sistema.
 */
@Validated
@Transactional
@Service
public class OrderDetailsService {

    private final OrderDetailsMapper mapper = OrderDetailsMapper.INSTANCE;

    @Autowired
    private OrderDetailsRepository repository;

    public List<OrderDetails> getAllOrders() {

        return mapper.toApiDomainList(repository.findAll());

    }

}


