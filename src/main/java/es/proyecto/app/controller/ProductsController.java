package es.proyecto.app.controller;

import es.proyecto.app.error.CategoryException;
import es.proyecto.app.error.ProductException;
import es.proyecto.app.error.UsersException;
import es.proyecto.app.service.ProductsService;
import es.swagger.codegen.api.ProductsApi;
import es.swagger.codegen.models.Orders;
import es.swagger.codegen.models.Products;
import es.swagger.codegen.models.ProductsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ProductsController es la implementación de ProductsApi.
 */
@Slf4j
@RestController
public class ProductsController implements ProductsApi {

    @Autowired
    private ProductsService productsService;

    @Override
    public ResponseEntity<Products> createProduct(Products body) {
        if (body == null) {
            log.error("Null body provided in createProduct");
            throw new ProductException("Null body provided");
        }
        if (body.getName() == null || body.getName().isEmpty()) {
            log.error("Invalid product name in createProduct");
            throw ProductException.MISSING_PRODUCT_NAME_EXCEPTION;
        }

        productsService.createProduct(body);
        log.info("Product created successfully: {}", body.getIdProduct());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer idProduct) {
        if (!isValidId(String.valueOf(idProduct))) {
            log.error("Invalid product ID format in deleteProduct: {}", idProduct);
            throw ProductException.INVALID_PRODUCT_ID_EXCEPTION;
        }

        Products deleteProduct = productsService.geProductById(idProduct);

        if (deleteProduct == null) {
            log.error("Product with id {} not found in deleteProduct", idProduct);
            throw ProductException.NO_PRODUCT_FOUND_EXCEPTION;
        }

        try {
            productsService.deleteProduct(idProduct);
            log.info("Product with id {} deleted successfully in deleteProduct", idProduct);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting product with id {} in deleteProduct: {}", idProduct, e.getMessage());
            throw new ProductException("Error deleting product with id " + idProduct);
        }
    }

    @Override
    public ResponseEntity<Products> getProductById(Integer idProduct) {
        try {
            if (!isValidId(String.valueOf(idProduct))) {
                log.error("Invalid product ID format in getProductById: {}", idProduct);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Products products = productsService.geProductById(idProduct);

            if (products == null) {
                log.info("Product with id {} not found in getProductById", idProduct);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("Product with id {} retrieved successfully in getProductById", idProduct);
                return new ResponseEntity<>(products, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error retrieving product with id {} in getProductById: {}", idProduct, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ProductsResponse> getProducts() {
        try {
            List<Products> productsList = productsService.getAllProducts();
            if (productsList.isEmpty()) {
                log.error("No products found in getProducts");
                throw ProductException.NO_PRODUCT_FOUND_EXCEPTION;
            }

            ProductsResponse response = new ProductsResponse();
            response.setProducts(productsList);
            log.info("Successfully fetched all products in getProducts");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProductException e) {
            log.error("Error fetching all products in getProducts: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Products> updateProduct(Integer idProduct, Products body) {
        try {
            if (body == null) {
                log.error("Null body provided in updateProduct");
                throw ProductException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdProduct() == null) {
                log.error("Product ID is required for updating in updateProduct");
                throw ProductException.MISSING_PRODUCT_ID_EXCEPTION;
            }

            Products existingProduct = productsService.geProductById(idProduct);
            if (existingProduct == null) {
                log.error("No product found with ID {} in updateProduct", idProduct);
                throw ProductException.NO_PRODUCT_FOUND_EXCEPTION;
            }
            HttpStatus status = productsService.updateProduct(idProduct, body);
            log.info("Product with id {} updated successfully in updateProduct", idProduct);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            log.error("Invalid ID format in updateProduct: {}", idProduct);
            throw ProductException.INVALID_PRODUCT_ID_EXCEPTION;
        } catch (Exception e) {
            log.error("Error updating product with id {} in updateProduct: {}", idProduct, e.getMessage());
            throw new ProductException("Error updating product");
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
