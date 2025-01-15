package es.proyecto.app.controller;

import es.proyecto.app.error.ProductException;
import es.proyecto.app.service.ProductsService;
import es.swagger.codegen.api.ProductsApi;
import es.swagger.codegen.models.Products;
import es.swagger.codegen.models.ProductsResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Override
    public ResponseEntity<Products> createProduct(Products body) {
        if (body == null) {
            logger.error("Null body provided in createProduct");
            throw ProductException.NULL_BODY_EXCEPTION;
        }
        if (body.getName() == null || body.getName().isEmpty()) {
            logger.error("Invalid product name in createProduct");
            throw ProductException.MISSING_PRODUCT_NAME_EXCEPTION;
        }

        productsService.createProduct(body);
        logger.info("Product created successfully: {}", body.getIdProduct());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer idProduct) {
        if (!isValidId(String.valueOf(idProduct))) {
            logger.error("Invalid product ID format in deleteProduct: {}", idProduct);
            throw ProductException.INVALID_PRODUCT_ID_EXCEPTION;
        }

        Products deleteProduct = productsService.geProductById(idProduct);

        if (deleteProduct == null) {
            logger.error("Product with id {} not found in deleteProduct", idProduct);
            throw ProductException.NO_PRODUCT_FOUND_EXCEPTION;
        }

        try {
            productsService.deleteProduct(idProduct);
            logger.info("Product with id {} deleted successfully in deleteProduct", idProduct);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting product with id {} in deleteProduct: {}", idProduct, e.getMessage());
            throw new ProductException("Error deleting product with id " + idProduct);
        }
    }

    @Override
    public ResponseEntity<Products> getProductById(Integer idProduct) {
        try {
            if (!isValidId(String.valueOf(idProduct))) {
                logger.error("Invalid product ID format in getProductById: {}", idProduct);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Products products = productsService.geProductById(idProduct);

            if (products == null) {
                logger.info("Product with id {} not found in getProductById", idProduct);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                logger.info("Product with id {} retrieved successfully in getProductById", idProduct);
                return new ResponseEntity<>(products, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error retrieving product with id {} in getProductById: {}", idProduct, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ProductsResponse> getProducts() {
        try {
            List<Products> productsList = productsService.getAllProducts();
            if (productsList.isEmpty()) {
                logger.error("No products found in getProducts");
                throw ProductException.NO_PRODUCT_FOUND_EXCEPTION;
            }

            ProductsResponse response = new ProductsResponse();
            response.setProducts(productsList);
            logger.info("Successfully fetched all products in getProducts");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProductException e) {
            logger.error("Error fetching all products in getProducts: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Products> updateProduct(Integer idProduct, Products body) {
        try {
            if (body == null) {
                logger.error("Null body provided in updateProduct");
                throw ProductException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdProduct() == null) {
                logger.error("Product ID is required for updating in updateProduct");
                throw ProductException.MISSING_PRODUCT_ID_EXCEPTION;
            }

            Products existingProduct = productsService.geProductById(idProduct);
            if (existingProduct == null) {
                logger.error("No product found with ID {} in updateProduct", idProduct);
                throw ProductException.NO_PRODUCT_FOUND_EXCEPTION;
            }
            HttpStatus status = productsService.updateProduct(idProduct, body);
            logger.info("Product with id {} updated successfully in updateProduct", idProduct);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            logger.error("Invalid ID format in updateProduct: {}", idProduct);
            throw ProductException.INVALID_PRODUCT_ID_EXCEPTION;
        } catch (Exception e) {
            logger.error("Error updating product with id {} in updateProduct: {}", idProduct, e.getMessage());
            throw  ProductException.ERROR_UPDATING_PRODUCT_EXCEPTION;
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
