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
 * RolesController es la implementación de RolesApi.
 */
@Slf4j
@RestController
public class SubcategoriesController implements SubcategoryApi {

    @Autowired
    private SubcategoriesService subcategoriesService;


    @Override
    public ResponseEntity<Subcategory> createSubcategory(Subcategory body) {
        if (body == null) {
            // log.error("Null body provided");
            throw new SubcategoryException("Null body provided");
        }

        if (body.getName() == null || body.getName().isEmpty()) {
            log.error("Invalid subcategory name");
            throw  SubcategoryException.MISSING_SUBCATEGORY_NAME_EXCEPTION;
        }

        //TODO: comprobación de no hacer una subcategoría con nombre repetido
        subcategoriesService.createSubcategory(body);

        log.info("subcategory created successfully: {}", body.getIdCategory());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteSubcategory(Integer idSubcategory) {
        if (!isValidId(String.valueOf(idSubcategory))) {
            log.error("Cant delete. Invalid subcategory ID format: {}", idSubcategory);
            throw SubcategoryException.INVALID_SUBCATEGORY_ID_EXCEPTION;
        }

        Subcategory deleteSubcategory = subcategoriesService.getSubcategoryById(idSubcategory);

        if (deleteSubcategory == null) {
            log.error("Subcategory with id {} not found", idSubcategory);
            throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
        }

        try {
            subcategoriesService.deleteSubcategory(idSubcategory);
            log.info("Subcategory with id {} deleted successfully", idSubcategory);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting Subcategory with id {}: {}", idSubcategory, e.getMessage());
            throw new UsersException("Error deleting Subcategory with id " + idSubcategory);
        }
    }

    @Override
    public ResponseEntity<SubcategoriesResponse> getSubcategories() {
        try {
            List<Subcategory> subcategoryList = subcategoriesService.getAllSubcategories();


            if (subcategoryList.isEmpty()) {
                throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
            }


            SubcategoriesResponse response = new SubcategoriesResponse();
            response.setSubcategories(subcategoryList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RolesException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Subcategory> getSubcategoryById(Integer idSubcategory) {
        try {
            if (!isValidId(String.valueOf(idSubcategory))) {
                log.error("Invalid subcategory ID format: {}", idSubcategory);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Subcategory subcategory = subcategoriesService.getSubcategoryById(idSubcategory);

            if (subcategory == null) {
                log.info("subcategory with id {} not found", idSubcategory);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("subcategory with id {} retrieved successfully", idSubcategory);
                return new ResponseEntity<>(subcategory, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error retrieving subcategory with id {}: {}", idSubcategory, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Subcategory> updateSubcategory(Integer idSubcategory, Subcategory body) {
        try {
            if (body == null) {
                log.error("Null body provided");
                throw CategoryException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdCategory() == null) {
                log.error("subcategory ID is required for updating");
                throw SubcategoryException.MISSING_SUBCATEGORY_ID_EXCEPTION;
            }

            Subcategory existingSubcategory = subcategoriesService.getSubcategoryById(idSubcategory);
            if (existingSubcategory == null) {
                log.error("No subcategory found with ID {}", idSubcategory);
                throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
            }
            HttpStatus status = subcategoriesService.updateSubcategory(idSubcategory, body);
            log.info("Subcategory with id {} updated successfully", idSubcategory);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            log.error("Invalid ID format: {}", idSubcategory);
            throw SubcategoryException.INVALID_SUBCATEGORY_ID_EXCEPTION;
        } catch (Exception e) {
            log.error("Error updating subcategory with id {}: {}", idSubcategory, e.getMessage());
            throw new SubcategoryException("Error updating subcategory");
        }
    }


    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
