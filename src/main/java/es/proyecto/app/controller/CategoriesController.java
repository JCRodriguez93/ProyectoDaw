package es.proyecto.app.controller;

import es.proyecto.app.error.CategoryException;
import es.proyecto.app.service.CategoriesService;
import es.swagger.codegen.api.CategoryApi;
import es.swagger.codegen.models.CategoriesResponse;
import es.swagger.codegen.models.Category;
import es.swagger.codegen.models.DeleteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CategoryController es la implementación de CategoryApi.
 */
@Slf4j
@RestController
public class CategoriesController implements CategoryApi {

    @Autowired
    private CategoriesService categoriesService;

    @Override
    public ResponseEntity<Category> createCategory(Category body) {
        if (body == null) {
            log.error("Null body provided in createCategory");
            throw new CategoryException("Null body provided in createCategory");
        }

        if (body.getName() == null || body.getName().isEmpty()) {
            log.error("Invalid category name in createCategory");
            throw CategoryException.MISSING_CATEGORY_NAME_EXCEPTION;
        }

        if (categoriesService.getCategoryByName(body.getName())) {
            log.error("Category already exists with name: {}", body.getName());
            throw CategoryException.CATEGORY_ALREADY_EXISTS_EXCEPTION;
        }
        categoriesService.createCategory(body);

        log.info("Category created successfully: {}", body.getIdCategory());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DeleteResponse> deleteCategory(Integer idCategory) {
        if (!isValidId(String.valueOf(idCategory))) {
            log.error("Invalid category ID format in deleteCategory: {}", idCategory);
            throw CategoryException.INVALID_CATEGORY_ID_EXCEPTION;
        }

        Category deleteCategory = categoriesService.getCategoryById(idCategory);

        if (deleteCategory == null) {
            log.error("Category with id {} not found in deleteCategory", idCategory);
            throw CategoryException.NO_CATEGORY_FOUND_EXCEPTION;
        }

        try {
            categoriesService.deleteCategory(idCategory);
            log.info("Category with id {} deleted successfully", idCategory);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting category with id {}: {}", idCategory, e.getMessage());
            throw new CategoryException("Error deleting category with id " + idCategory);
        }
    }

    @Override
    public ResponseEntity<CategoriesResponse> getCategories() {
        try {
            List<Category> categoryList = categoriesService.getAllCategories();
            if (categoryList.isEmpty()) {
                throw CategoryException.NO_CATEGORY_FOUND_EXCEPTION;
            }
            CategoriesResponse response = new CategoriesResponse();
            response.setCategories(categoryList);
            log.info("Successfully retrieved categories in getCategories");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CategoryException e) {
            log.error("Error retrieving categories in getCategories: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Category> getCategoryById(Integer idCategory) {
        try {
            if (!isValidId(String.valueOf(idCategory))) {
                log.error("Invalid category ID format in getCategoryById: {}", idCategory);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Category category = categoriesService.getCategoryById(idCategory);

            if (category == null) {
                log.info("Category with id {} not found in getCategoryById", idCategory);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("Category with id {} retrieved successfully in getCategoryById", idCategory);
                return new ResponseEntity<>(category, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error retrieving category with id {} in getCategoryById: {}", idCategory, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> updateCategory(Integer idCategory, Category body) {
        try {
            if (body == null) {
                log.error("Null body provided in updateCategory");
                throw CategoryException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdCategory() == null) {
                log.error("Category ID is required for updating in updateCategory");
                throw CategoryException.MISSING_CATEGORY_ID_EXCEPTION;
            }

            Category existingCategory = categoriesService.getCategoryById(idCategory);
            if (existingCategory == null) {
                log.error("No category found with ID {} in updateCategory", idCategory);
                throw CategoryException.NO_CATEGORY_FOUND_EXCEPTION;
            }
            HttpStatus status = categoriesService.updateCategory(idCategory, body);
            log.info("Category with id {} updated successfully in updateCategory", idCategory);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            log.error("Invalid ID format in updateCategory: {}", idCategory);
            throw CategoryException.INVALID_CATEGORY_ID_EXCEPTION;
        } catch (Exception e) {
            log.error("Error updating category with id {} in updateCategory: {}", idCategory, e.getMessage());
            throw new CategoryException("Error updating category");
        }
    }

    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            log.error("Invalid ID format in isValidId: {}", id);
            return false;
        }
    }
}
