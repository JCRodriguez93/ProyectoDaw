package es.proyecto.app.controller;


import es.proyecto.app.entity.RolesEntity;
import es.proyecto.app.error.RolesException;
import es.proyecto.app.error.SubcategoryException;
import es.proyecto.app.service.RolesService;
import es.proyecto.app.service.SubcategoriesService;
import es.swagger.codegen.api.RolesApi;
import es.swagger.codegen.api.SubcategoryApi;
import es.swagger.codegen.models.Role;
import es.swagger.codegen.models.RoleResponse;
import es.swagger.codegen.models.SubcategoriesResponse;
import es.swagger.codegen.models.Subcategory;
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
    public ResponseEntity<SubcategoriesResponse> getSubcategories() {
        try {
            List<Subcategory> subcategoryList = subcategoriesService.getAllSubcategories();


            if (subcategoryList.isEmpty()) {
                log.error("No subcategories found");
                throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
            }


            SubcategoriesResponse response = new SubcategoriesResponse();
            response.setSubcategories(subcategoryList);
            log.info("Successfully fetched all subcategories");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RolesException e) {
            log.error("Error fetching all subcategories: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
