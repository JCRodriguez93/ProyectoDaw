package es.proyecto.app.service;

import es.proyecto.app.mapper.CategoryMapper;
import es.proyecto.app.mapper.OrderMapper;
import es.proyecto.app.repository.CategoryRepository;
import es.proyecto.app.repository.OrdersRepository;
import es.swagger.codegen.models.Category;
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
public class OrdersService {

    private final OrderMapper mapper = OrderMapper.INSTANCE;

    @Autowired
    private OrdersRepository repository;

    public List<Orders> getAllOrders() {

        return mapper.toApiDomainList(repository.findAll());

    }

}


