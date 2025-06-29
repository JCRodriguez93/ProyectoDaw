package es.proyecto.app.service;

import es.proyecto.app.entity.CategoryEntity;
import es.proyecto.app.mapper.CategoryMapper;
import es.proyecto.app.repository.CategoryRepository;
import es.swagger.codegen.models.Category;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de categorías del sistema.
 * Proporciona operaciones para crear, obtener, actualizar y eliminar categorías.
 */
@Validated
@Transactional
@Service
public class CategoriesService {

    @Autowired
    private CategoryMapper mapper; //sin el INSTANCE ya aparecen las subcategorias en vez de null.

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Obtiene todas las categorías existentes en el sistema.
     *
     * @return Lista de categorías.
     */
    public List<Category> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        return mapper.toApiDomain(categoryEntities); // MapStruct para convertir entidades a modelos

    }

    /**
     * Crea una nueva categoría en el sistema.
     *
     * @param category Objeto categoría con los datos para crear.
     */
    public void createCategory(Category category) {
        CategoryEntity entity = mapper.toEntity(category);
        categoryRepository.save(entity);
    }

    /**
     * Obtiene una categoría por su identificador.
     *
     * @param idCategory Identificador de la categoría.
     * @return La categoría encontrada o {@code null} si no existe.
     */
    public Category getCategoryById(Integer idCategory) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(idCategory);
        return optionalCategoryEntity.map(mapper::toApiDomain).orElse(null);
    }

    /**
     * Comprueba si existe una categoría por su nombre.
     *
     * @param name Nombre de la categoría.
     * @return {@code true} si la categoría existe; {@code false} en caso contrario.
     */
    public boolean getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name).isPresent();
    }

    /**
     * Elimina una categoría por su identificador, si existe.
     *
     * @param idCategory Identificador de la categoría a eliminar.
     */
    public void deleteCategory(Integer idCategory) {
        if (categoryRepository.existsById(idCategory)) {
            categoryRepository.deleteById(idCategory);
        }
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param idCategory Identificador de la categoría a actualizar.
     * @param category   Objeto categoría con los nuevos datos.
     * @return {@link HttpStatus#OK} si se actualizó correctamente,
     *         {@link HttpStatus#NOT_FOUND} si la categoría no existe.
     */
    public HttpStatus updateCategory(Integer idCategory, Category category) {
        Optional<CategoryEntity> existingCategory = categoryRepository.findById(idCategory);
        if (existingCategory.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        category.setIdCategory(idCategory);
        categoryRepository.save(mapper.toEntity(category));
        return HttpStatus.OK;
    }

}


