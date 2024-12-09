package es.proyecto.app.controller;


import es.proyecto.app.error.CategoryException;
import es.proyecto.app.error.ProductException;
import es.proyecto.app.service.ProductsService;
import es.swagger.codegen.api.ProductsApi;
import es.swagger.codegen.models.CategoriesResponse;
import es.swagger.codegen.models.Products;
import es.swagger.codegen.models.ProductsResponse;
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
public class ProductsController implements ProductsApi {

    @Autowired
    private ProductsService productsService;


    @Override
    public ResponseEntity<ProductsResponse> getProducts() {
        try {
            List<Products> productsList = productsService.getAllProducts();


            if (productsList.isEmpty()) {
                log.error("No categories found");
                throw ProductException.NO_PRODUCT_FOUND_EXCEPTION;
            }


            ProductsResponse response = new ProductsResponse();
            response.setProducts(productsList);
            log.info("Successfully fetched all products");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CategoryException e) {
            log.error("Error fetching all products: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
