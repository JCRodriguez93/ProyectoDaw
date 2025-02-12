package es.proyecto.app.Controller;

import es.proyecto.app.controller.ProductsController;
import es.proyecto.app.error.ProductException;
import es.proyecto.app.service.ProductsService;
import es.swagger.codegen.models.Products;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductsControllerTest {

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private ProductsController productsController;

    private Products validProduct;
    private Products invalidProduct;

    @BeforeEach
    public void setUp() {
        // Inicializamos los productos
        validProduct = new Products();
        validProduct.setIdProduct(1);
        validProduct.setIdSubcategory(1);
        validProduct.setName("Producto válido");
        validProduct.setDescription("Descripción válida");
        validProduct.setPrice(99.99);
        validProduct.setImageUrl("http://example.com/image.jpg");

        invalidProduct = new Products();
        invalidProduct.setIdProduct(2);
        invalidProduct.setIdSubcategory(2);
        invalidProduct.setName(null);  // Nombre nulo para la prueba de error
        invalidProduct.setDescription("Descripción inválida");
        invalidProduct.setPrice(0.0);
        invalidProduct.setImageUrl("http://example.com/image_invalid.jpg");
    }

    @Test
    @DisplayName("Crear producto con datos válidos")
    public void createProductWithValidDataAndThenReturnsCreated() {
        // Simulamos el comportamiento del servicio de creación de productos
        when(productsService.createProduct(validProduct)).thenReturn(HttpStatus.CREATED);

        // Llamamos al método del controlador
        ResponseEntity<Products> response = productsController.createProduct(validProduct);

        // Verificamos que la respuesta sea correcta (creación exitosa)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificamos que el servicio fue llamado con los argumentos esperados
        verify(productsService, times(1)).createProduct(validProduct);
    }
    @Test
    @DisplayName("Crear producto con nombre nulo")
    public void createProductWithNullNameAndThenReturnsBadRequest() {
        // Comprobamos que se lanza la excepción esperada
        ProductException exception = assertThrows(ProductException.class, () -> {
            productsController.createProduct(invalidProduct);
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("Product name is required", exception.getMessage());
    }
    @Test
    @DisplayName("Obtener producto por ID")
    public void getProductByIdAndThenReturnOk() {
        // Simulamos que el producto existe
        when(productsService.geProductById(1)).thenReturn(validProduct);

        // Llamamos al método del controlador para obtener el producto por ID
        ResponseEntity<Products> response = productsController.getProductById(1);

        // Verificamos que se retorna un status OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verificamos que el producto retornado sea el esperado
        assertEquals(validProduct, response.getBody());
    }
    @Test
    @DisplayName("Actualizar producto con datos válidos")
    public void updateProductWithValidDataAndThenReturnOk() {
        // Producto existente en la base de datos
        when(productsService.geProductById(1)).thenReturn(validProduct);

        // Creamos un producto actualizado con nuevos datos
        Products updatedProduct = new Products();
        updatedProduct.setIdProduct(1);
        updatedProduct.setIdSubcategory(2);
        updatedProduct.setName("Producto actualizado");
        updatedProduct.setDescription("Descripción actualizada");
        updatedProduct.setPrice(199.99);
        updatedProduct.setImageUrl("http://example.com/updated_image.jpg");

        // Simulamos que el producto se actualiza correctamente
        when(productsService.updateProduct(updatedProduct.getIdProduct(), updatedProduct)).thenReturn(HttpStatus.OK);

        // Llamamos al método del controlador para actualizar el producto
        ResponseEntity<Products> response = productsController.updateProduct(updatedProduct.getIdProduct(), updatedProduct);

        // Verificamos que se retorne un status OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificamos que el servicio fue llamado con los argumentos esperados
        verify(productsService, times(1)).updateProduct(updatedProduct.getIdProduct(), updatedProduct);
    }
    @Test
    @DisplayName("Eliminar producto")
    public void deleteProductAndThenReturnNoContent() {
        // Simulamos el comportamiento del servicio de eliminación de productos
        when(productsService.geProductById(1)).thenReturn(validProduct);

        // Llamamos al método del controlador para eliminar el producto
        ResponseEntity<Void> response = productsController.deleteProduct(1);

        // Verificamos que se retorne un status NO_CONTENT (204)
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verificamos que el servicio fue llamado con los argumentos esperados
        verify(productsService, times(1)).deleteProduct(1);
    }

    @Test
    @DisplayName("Intentar eliminar producto inexistente")
    public void deleteNonExistentProductAndThenReturnNotFound() {
        // Simulamos que el servicio lanza la excepción ProductException cuando el producto no existe
        when(productsService.geProductById(999)).thenThrow(new ProductException("No product found with the specified ID"));

        // Usamos try-catch para capturar la excepción y verificar la respuesta
        try {
            productsController.deleteProduct(999);
        } catch (ProductException e) {
            // Verificamos que la excepción sea la esperada
            assertEquals("No product found with the specified ID", e.getMessage());

        }

        // Verificamos que el servicio no fue llamado para eliminar un producto
        verify(productsService, times(0)).deleteProduct(999);
    }








}
