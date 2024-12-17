package es.proyecto.app.service;

import es.proyecto.app.mapper.ProductMapper;
import es.proyecto.app.repository.ProductsRepository;
import es.swagger.codegen.models.Products;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Servicio de Users para la gestión de usuarios en el sistema.
 */
@Validated
@Transactional
@Service
public class ProductsService {

    private final ProductMapper mapper = ProductMapper.INSTANCE;

    @Autowired
    private ProductsRepository repository;

    public List<Products> getAllProducts() {
        return mapper.toApiDomain(repository.findAll());
    }



}


