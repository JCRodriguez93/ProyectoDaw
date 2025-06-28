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

    /**
     * Crea un nuevo producto en el sistema.
     * <p>
     * Valida que el objeto {@code body} no sea nulo y que el nombre del producto esté presente y no vacío.
     * En caso de validaciones fallidas, lanza excepciones específicas.
     * Si todo es correcto, delega la creación del producto al servicio {@code productsService} y devuelve
     * una respuesta HTTP con código 201 (CREATED).
     *
     * @param body el objeto {@link Products} que contiene los datos del producto a crear
     * @return una {@link ResponseEntity} con código de estado 201 (CREATED) en caso de éxito
     * @throws ProductException si el cuerpo es nulo o el nombre del producto no es válido
     */
    @Override
    public ResponseEntity<Products> createProduct(Products body) {
        if (body == null) {
            logger.error("Null body provided");
            throw ProductException.NULL_BODY_EXCEPTION;
        }
        if (body.getName() == null || body.getName().isEmpty()) {
            logger.error("Invalid product name");
            throw ProductException.MISSING_PRODUCT_NAME_EXCEPTION;
        }

        productsService.createProduct(body);
        logger.info("Product created successfully: {}", body.getIdProduct());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Elimina un producto existente identificado por su ID.
     * <p>
     * Válida el formato del ID del producto y verifica que el producto exista antes de proceder a su eliminación.
     * En caso de ID inválido o producto no encontrado, lanza excepciones específicas.
     * Si la eliminación falla por alguna razón, captura la excepción y lanza una excepción personalizada.
     *
     * @param idProduct el identificador del producto a eliminar
     * @return una {@link ResponseEntity} con código HTTP 204 (NO_CONTENT) si la eliminación fue exitosa
     * @throws ProductException si el ID es inválido, el producto no existe o ocurre un error al eliminar
     */
    @Override
    public ResponseEntity<Void> deleteProduct(Integer idProduct) {
        if (!isValidId(String.valueOf(idProduct))) {
            logger.error("Invalid product ID format: {}", idProduct);
            throw ProductException.INVALID_PRODUCT_ID_EXCEPTION;
        }

        Products deleteProduct = productsService.geProductById(idProduct);

        if (deleteProduct == null) {
            logger.error("Product with id {} not found", idProduct);
            throw ProductException.NO_PRODUCT_FOUND_EXCEPTION;
        }

        try {
            productsService.deleteProduct(idProduct);
            logger.info("Product with id {} deleted successfully", idProduct);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting product with id {}. Message: {}", idProduct, e.getMessage());
            throw new ProductException("Error deleting product with id " + idProduct);
        }
    }

    /**
     * Obtiene un producto por su identificador.
     * <p>
     * Valida que el ID proporcionado tenga un formato válido. Si el ID no es válido, devuelve
     * una respuesta con estado HTTP 400 (BAD_REQUEST). Si no se encuentra el producto,
     * devuelve estado HTTP 404 (NOT_FOUND). En caso de éxito, devuelve el producto y
     * estado HTTP 200 (OK).
     * <p>
     * En caso de cualquier excepción, devuelve estado HTTP 500 (INTERNAL_SERVER_ERROR).
     *
     * @param idProduct el identificador del producto a buscar
     * @return una {@link ResponseEntity} que contiene el producto y el código HTTP correspondiente
     */
    @Override
    public ResponseEntity<Products> getProductById(Integer idProduct) {
        try {
            if (!isValidId(String.valueOf(idProduct))) {
                logger.error("Cannot get product by ID {}. Invalid ID format", idProduct);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Products products = productsService.geProductById(idProduct);

            if (products == null) {
                logger.info("Product with id {} not found", idProduct);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                logger.info("Product with id {} retrieved successfully", idProduct);
                return new ResponseEntity<>(products, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error retrieving product with id {}. Message: {}", idProduct, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todos los productos disponibles.
     * <p>
     * Recupera la lista completa de productos a través del servicio de productos.
     * Si no se encuentran productos, lanza una excepción {@link ProductException}.
     * En caso de éxito, devuelve una respuesta con estado HTTP 200 (OK) y la lista de productos.
     * Si ocurre un error durante la obtención, devuelve estado HTTP 500 (INTERNAL_SERVER_ERROR).
     *
     * @return una {@link ResponseEntity} que contiene un objeto {@link ProductsResponse} con la lista de productos y el código HTTP correspondiente
     */
    @Override
    public ResponseEntity<ProductsResponse> getProducts() {
        try {
            List<Products> productsList = productsService.getAllProducts();
            if (productsList.isEmpty()) {
                logger.error("No products found");
                throw ProductException.NO_PRODUCT_FOUND_EXCEPTION;
            }

            ProductsResponse response = new ProductsResponse();
            response.setProducts(productsList);
            logger.info("Successfully fetched all products");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProductException e) {
            logger.error("Error fetching all products: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza un producto existente identificado por su ID.
     * <p>
     * Valida que el cuerpo del producto no sea nulo y que contenga un ID válido.
     * Luego verifica que el producto con el ID dado exista.
     * Si alguna validación falla, lanza la excepción correspondiente de {@link ProductException}.
     * En caso de éxito, actualiza el producto y devuelve el estado HTTP resultante.
     * <p>
     * También captura errores de formato de ID y excepciones generales, lanzando excepciones específicas.
     *
     * @param idProduct el identificador del producto que se desea actualizar
     * @param body el objeto {@link Products} con los datos actualizados del producto
     * @return un {@link ResponseEntity} que contiene el estado HTTP de la operación
     * @throws ProductException si el cuerpo es nulo, falta el ID, el producto no existe, el ID es inválido, o ocurre un error durante la actualización
     */
    @Override
    public ResponseEntity<Products> updateProduct(Integer idProduct, Products body) {
        try {
            if (body == null) {
                logger.error("Cannot update product. Null body provided");
                throw ProductException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdProduct() == null) {
                logger.error("Product ID is required for updating");
                throw ProductException.MISSING_PRODUCT_ID_EXCEPTION;
            }

            Products existingProduct = productsService.geProductById(idProduct);
            if (existingProduct == null) {
                logger.error("No product found with ID {}", idProduct);
                throw ProductException.NO_PRODUCT_FOUND_EXCEPTION;
            }
            HttpStatus status = productsService.updateProduct(idProduct, body);
            logger.info("Product with id {} updated successfully", idProduct);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            logger.error("Invalid ID format: {}", idProduct);
            throw ProductException.INVALID_PRODUCT_ID_EXCEPTION;
        } catch (Exception e) {
            logger.error("Error updating product with id {}. Message: {}", idProduct, e.getMessage());
            throw  ProductException.ERROR_UPDATING_PRODUCT_EXCEPTION;
        }
    }

    /**
     * Verifica si una cadena dada representa un identificador numérico válido.
     * <p>
     * Este método intenta convertir la cadena a un {@code Integer}. Si la conversión tiene éxito,
     * se considera un ID válido. Si lanza una excepción {@code NumberFormatException},
     * se considera inválido.
     *
     * @param id la cadena a validar como identificador numérico.
     * @return {@code true} si la cadena puede convertirse a {@code Integer},
     *         {@code false} en caso contrario.
     */
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
