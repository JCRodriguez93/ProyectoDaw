package es.proyecto.app.service;

import es.proyecto.app.entity.CategoryEntity;
import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.entity.UsersEntity;
import es.proyecto.app.error.UsersException;
import es.proyecto.app.mapper.CategoryMapper;
import es.proyecto.app.repository.CategoryRepository;
import es.swagger.codegen.models.Category;
import es.swagger.codegen.models.User;
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
public class CategoriesService {

    private final CategoryMapper mapper = CategoryMapper.INSTANCE;

    @Autowired
    private CategoryRepository repository;

    public List<Category> getAllCategories() {
        return mapper.toApiDomain(repository.findAll());
    }

    public HttpStatus createCategory(Category idCategory) {
        CategoryEntity entity = mapper.toEntity(idCategory);
        repository.save(entity);
        return HttpStatus.CREATED;
    }


    public Category getCategoryById(Integer idCategory) {
        Optional<CategoryEntity> optionalCategoryEntity = repository.findById(idCategory);
        return optionalCategoryEntity.map(mapper::toApiDomain).orElse(null);
    }

    public boolean deleteCategory(Integer idCategory) {
        if (repository.existsById(idCategory)) {
            repository.deleteById(idCategory);
            return true;
        } else {
            return false;
        }
    }

    public HttpStatus updateCategory(Integer idUser, Category user) {

        Optional<CategoryEntity> existingUser = repository.findById(idUser);
        if(existingUser.isEmpty()){
            return HttpStatus.NOT_FOUND;
        }
        user.setIdCategory(idUser);
        repository.save(mapper.toEntity(user));
        return HttpStatus.OK;
    }

}


