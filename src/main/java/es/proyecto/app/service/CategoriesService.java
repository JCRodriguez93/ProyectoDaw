package es.proyecto.app.service;

import es.proyecto.app.mapper.CategoryMapper;
import es.proyecto.app.repository.CategoryRepository;
import es.swagger.codegen.models.Category;
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
public class CategoriesService {

    private final CategoryMapper mapper = CategoryMapper.INSTANCE;

    @Autowired
    private CategoryRepository repository;

    public List<Category> getAllCategories() {
        return mapper.toApiDomain(repository.findAll());
    }

}


