package es.proyecto.app.controller;


import es.proyecto.app.error.CategoryException;
import es.proyecto.app.service.CategoriesService;
import es.swagger.codegen.api.CategoryApi;
import es.swagger.codegen.models.CategoriesResponse;
import es.swagger.codegen.models.Category;

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
public class CategoriesController implements CategoryApi {

    @Autowired
    private CategoriesService categoriesService;


    @Override
    public ResponseEntity<CategoriesResponse> getCategories() {
        try {
            List<Category> categoryList = categoriesService.getAllCategories();


            if (categoryList.isEmpty()) {
                throw CategoryException.NO_CATEGORY_FOUND_EXCEPTION;
            }


            CategoriesResponse response = new CategoriesResponse();
            response.setCategories(categoryList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CategoryException e) {
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
