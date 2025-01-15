package es.proyecto.app.controller;


import es.proyecto.app.error.SubcategoryException;
import es.proyecto.app.service.SubcategoriesService;
import es.swagger.codegen.api.SubcategoryApi;
import es.swagger.codegen.models.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(SubcategoriesController.class);

    @Override
    public ResponseEntity<Subcategory> createSubcategory(Subcategory body) {
        if (body == null) {
            logger.error("Null body provided in createSubcategory");
            throw SubcategoryException.NULL_BODY_EXCEPTION;
        }

        if (body.getName() == null || body.getName().isEmpty()) {
            logger.error("Invalid subcategory name in createSubcategory");
            throw SubcategoryException.MISSING_SUBCATEGORY_NAME_EXCEPTION;
        }

        // Comprobación de no hacer una subcategoría con nombre repetido
        if (subcategoriesService.existsByNameAndCategory(body.getName(), body.getIdCategory())) {
            logger.error("Duplicate subcategory name in createSubcategory");
            throw SubcategoryException.DUPLICATE_SUBCATEGORY_NAME_EXCEPTION;
        }

        subcategoriesService.createSubcategory(body);
        logger.info("Subcategory created successfully: {}", body.getIdCategory());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @Override
    public ResponseEntity<Void> deleteSubcategory(Integer idSubcategory) {
        if (!isValidId(String.valueOf(idSubcategory))) {
            logger.error("Can't delete. Invalid subcategory ID format in deleteSubcategory: {}", idSubcategory);
            throw SubcategoryException.INVALID_SUBCATEGORY_ID_EXCEPTION;
        }

        Subcategory deleteSubcategory = subcategoriesService.getSubcategoryById(idSubcategory);

        if (deleteSubcategory == null) {
            logger.error("Subcategory with id {} not found in deleteSubcategory", idSubcategory);
            throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
        }

        try {
            subcategoriesService.deleteSubcategory(idSubcategory);
            logger.info("Subcategory with id {} deleted successfully in deleteSubcategory", idSubcategory);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting subcategory with id {} in deleteSubcategory: {}", idSubcategory, e.getMessage());
            throw new SubcategoryException("Error deleting subcategory with id " + idSubcategory);
        }
    }

    @Override
    public ResponseEntity<SubcategoriesResponse> getSubcategories() {
        try {
            List<Subcategory> subcategoryList = subcategoriesService.getAllSubcategories();

            if (subcategoryList.isEmpty()) {
                logger.error("No subcategories found in getSubcategories");
                throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
            }

            SubcategoriesResponse response = new SubcategoriesResponse();
            response.setSubcategories(subcategoryList);
            logger.info("Successfully fetched all subcategories in getSubcategories");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SubcategoryException e) {
            logger.error("Error fetching all subcategories in getSubcategories: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Subcategory> getSubcategoryById(Integer idSubcategory) {
        try {
            if (!isValidId(String.valueOf(idSubcategory))) {
                logger.error("Invalid subcategory ID format in getSubcategoryById: {}", idSubcategory);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Subcategory subcategory = subcategoriesService.getSubcategoryById(idSubcategory);

            if (subcategory == null) {
                logger.info("Subcategory with id {} not found in getSubcategoryById", idSubcategory);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("Subcategory with id {} retrieved successfully in getSubcategoryById", idSubcategory);
                return new ResponseEntity<>(subcategory, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error retrieving subcategory with id {} in getSubcategoryById: {}", idSubcategory, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Subcategory> updateSubcategory(Integer idSubcategory, Subcategory body) {
        try {
            if (body == null) {
                logger.error("Null body provided in updateSubcategory");
                throw SubcategoryException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdCategory() == null) {
                logger.error("Subcategory ID is required for updating in updateSubcategory");
                throw SubcategoryException.MISSING_SUBCATEGORY_ID_EXCEPTION;
            }

            Subcategory existingSubcategory = subcategoriesService.getSubcategoryById(idSubcategory);
            if (existingSubcategory == null) {
                logger.error("No subcategory found with ID {} in updateSubcategory", idSubcategory);
                throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
            }
            HttpStatus status = subcategoriesService.updateSubcategory(idSubcategory, body);
            logger.info("Subcategory with id {} updated successfully in updateSubcategory", idSubcategory);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            logger.error("Invalid ID format in updateSubcategory: {}", idSubcategory);
            throw SubcategoryException.INVALID_SUBCATEGORY_ID_EXCEPTION;
        } catch (Exception e) {
            logger.error("Error updating subcategory with id {} in updateSubcategory: {}", idSubcategory, e.getMessage());
            throw SubcategoryException.ERROR_UPDATING_SUBCATEGORY_EXCEPTION;
        }
    }

    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            logger.error("Invalid ID format in isValidId: {}", id);
            return false;
        }
    }
}
