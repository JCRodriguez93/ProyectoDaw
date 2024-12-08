package es.proyecto.app.service;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.mapper.CategoryMapper;
import es.proyecto.app.mapper.RolesMapper;
import es.proyecto.app.repository.CategoryRepository;
import es.proyecto.app.repository.RolesRepository;
import es.swagger.codegen.models.Category;
import es.swagger.codegen.models.Role;
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



//    public RolesEntity getCategoryById(Integer id) {
//
//        return repository.findById(id).orElse(null);
//    }
//    public void updateRole(RolesEntity rolesEntity) {
//        repository.save(rolesEntity);
//    }
}


