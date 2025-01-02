package es.proyecto.app.controller;

import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.error.CategoryException;
import es.proyecto.app.error.RolesException;
import es.proyecto.app.error.SubcategoryException;
import es.proyecto.app.error.UsersException;
import es.proyecto.app.service.RolesService;
import es.proyecto.app.service.SubcategoriesService;
import es.swagger.codegen.api.RolesApi;
import es.swagger.codegen.api.SubcategoryApi;
import es.swagger.codegen.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SubcategoriesController es la implementación de SubcategoryApi.
 */
@Slf4j
@RestController
public class SubcategoriesController implements SubcategoryApi {

    @Autowired
    private SubcategoriesService subcategoriesService;

    @Override
    public ResponseEntity<Subcategory> createSubcategory(Subcategory body) {
        if (body == null) {
            log.error("Null body provided in createSubcategory");
            throw SubcategoryException.NULL_BODY_EXCEPTION;
        }

        if (body.getName() == null || body.getName().isEmpty()) {
            log.error("Invalid subcategory name in createSubcategory");
            throw SubcategoryException.MISSING_SUBCATEGORY_NAME_EXCEPTION;
        }

        // Comprobación de no hacer una subcategoría con nombre repetido
        if (subcategoriesService.existsByNameAndCategory(body.getName(), body.getIdCategory())) {
            log.error("Duplicate subcategory name in createSubcategory");
            throw SubcategoryException.DUPLICATE_SUBCATEGORY_NAME_EXCEPTION;
        }

        subcategoriesService.createSubcategory(body);
        log.info("Subcategory created successfully: {}", body.getIdCategory());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @Override
    public ResponseEntity<Void> deleteSubcategory(Integer idSubcategory) {
        if (!isValidId(String.valueOf(idSubcategory))) {
            log.error("Can't delete. Invalid subcategory ID format in deleteSubcategory: {}", idSubcategory);
            throw SubcategoryException.INVALID_SUBCATEGORY_ID_EXCEPTION;
        }

        Subcategory deleteSubcategory = subcategoriesService.getSubcategoryById(idSubcategory);

        if (deleteSubcategory == null) {
            log.error("Subcategory with id {} not found in deleteSubcategory", idSubcategory);
            throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
        }

        try {
            subcategoriesService.deleteSubcategory(idSubcategory);
            log.info("Subcategory with id {} deleted successfully in deleteSubcategory", idSubcategory);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting subcategory with id {} in deleteSubcategory: {}", idSubcategory, e.getMessage());
            throw new SubcategoryException("Error deleting subcategory with id " + idSubcategory);
        }
    }

    @Override
    public ResponseEntity<SubcategoriesResponse> getSubcategories() {
        try {
            List<Subcategory> subcategoryList = subcategoriesService.getAllSubcategories();

            if (subcategoryList.isEmpty()) {
                log.error("No subcategories found in getSubcategories");
                throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
            }

            SubcategoriesResponse response = new SubcategoriesResponse();
            response.setSubcategories(subcategoryList);
            log.info("Successfully fetched all subcategories in getSubcategories");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SubcategoryException e) {
            log.error("Error fetching all subcategories in getSubcategories: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Subcategory> getSubcategoryById(Integer idSubcategory) {
        try {
            if (!isValidId(String.valueOf(idSubcategory))) {
                log.error("Invalid subcategory ID format in getSubcategoryById: {}", idSubcategory);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Subcategory subcategory = subcategoriesService.getSubcategoryById(idSubcategory);

            if (subcategory == null) {
                log.info("Subcategory with id {} not found in getSubcategoryById", idSubcategory);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("Subcategory with id {} retrieved successfully in getSubcategoryById", idSubcategory);
                return new ResponseEntity<>(subcategory, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error retrieving subcategory with id {} in getSubcategoryById: {}", idSubcategory, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Subcategory> updateSubcategory(Integer idSubcategory, Subcategory body) {
        try {
            if (body == null) {
                log.error("Null body provided in updateSubcategory");
                throw SubcategoryException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdCategory() == null) {
                log.error("Subcategory ID is required for updating in updateSubcategory");
                throw SubcategoryException.MISSING_SUBCATEGORY_ID_EXCEPTION;
            }

            Subcategory existingSubcategory = subcategoriesService.getSubcategoryById(idSubcategory);
            if (existingSubcategory == null) {
                log.error("No subcategory found with ID {} in updateSubcategory", idSubcategory);
                throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
            }
            HttpStatus status = subcategoriesService.updateSubcategory(idSubcategory, body);
            log.info("Subcategory with id {} updated successfully in updateSubcategory", idSubcategory);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            log.error("Invalid ID format in updateSubcategory: {}", idSubcategory);
            throw SubcategoryException.INVALID_SUBCATEGORY_ID_EXCEPTION;
        } catch (Exception e) {
            log.error("Error updating subcategory with id {} in updateSubcategory: {}", idSubcategory, e.getMessage());
            throw new SubcategoryException("Error updating subcategory");
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
