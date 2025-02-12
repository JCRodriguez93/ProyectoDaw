package es.proyecto.app.Controller;

import es.proyecto.app.controller.CategoriesController;
import es.proyecto.app.error.CategoryException;
import es.proyecto.app.service.CategoriesService;
import es.swagger.codegen.models.Category;
import es.swagger.codegen.models.DeleteResponse;
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
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoriesControllerTest {

    @Mock
    private CategoriesService categoriesService;

    @InjectMocks
    private CategoriesController categoriesController;

    private Category validCategory;
    private Category invalidCategory;

    @BeforeEach
    public void setUp() {
        // Inicializamos las categorías con la clase Category de Swagger
        validCategory = new Category();
        validCategory.setName("Tatuajes");
        validCategory.setIdCategory(1);


        invalidCategory = new Category();
        invalidCategory.setName(null);  // Nombre nulo para la prueba de error
        invalidCategory.setIdCategory(2);
    }
    @Test
    @DisplayName("Crear categoría con datos válidos")
    public void createCategoryWithValidDataAndThenReturnsCreated() {
        // Simulamos la respuesta del servicio
        when(categoriesService.getCategoryByName(validCategory.getName())).thenReturn(false);

        // Llamamos al método del controlador
        ResponseEntity<Category> response = categoriesController.createCategory(validCategory);

        // Verificamos que la respuesta sea correcta (creación exitosa)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    @DisplayName("Crear categoría con nombre vacío")
    public void createCategoryWithEmptyNameAndThenReturnsBadRequest() {
        // Modificamos el nombre para que esté vacío
        invalidCategory.setName("");

        // Comprobamos que se lanza la excepción esperada
        CategoryException exception = assertThrows(CategoryException.class, () -> {
            categoriesController.createCategory(invalidCategory);
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("Category name is required", exception.getMessage());
    }

    @Test
    @DisplayName("Crear categoría con nombre nulo")
    public void createCategoryWithNullNameAndThenReturnsBadRequest() {
        // Comprobamos que se lanza la excepción esperada
        CategoryException exception = assertThrows(CategoryException.class, () -> {
            categoriesController.createCategory(invalidCategory);
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("Category name is required", exception.getMessage());
    }

    @Test
    @DisplayName("Modificar categoría con datos válidos")
    public void updateCategoryWithValidDataThenReturnOK() {
        // Creamos una nueva categoría con los datos actualizados.
        Category updatedCategory = new Category();
        updatedCategory.setIdCategory(validCategory.getIdCategory()); // Aseguramos que el ID se mantiene
        updatedCategory.setName("Tatuajes Actualizados");

        // Simulamos que la categoría original existe
        when(categoriesService.getCategoryById(validCategory.getIdCategory())).thenReturn(validCategory);
        // Simulamos que el servicio de actualización devuelve OK
        when(categoriesService.updateCategory(validCategory.getIdCategory(), updatedCategory))
                .thenReturn(HttpStatus.OK);

        // Llamamos al método del controlador para actualizar la categoría
        ResponseEntity<Void> response = categoriesController.updateCategory(validCategory.getIdCategory(), updatedCategory);

        // Verificamos que se retorna un status OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // (Opcional) Verificar que se haya llamado al servicio de actualización; en este ejemplo lo dejamos implícito.
    }

    @Test
    @DisplayName("Modificar categoría inexistente")
    public void updateCategoryButCategoryNotFound() {
        // Creamos una categoría para actualización
        Category updatedCategory = new Category();
        updatedCategory.setName("CualquierNombre");  // Nombre para identificar el cambio
        updatedCategory.setIdCategory(999); // Un ID que no existe en la base de datos

        // Simulamos que al buscar la categoría con ID 999 no se encuentra
        when(categoriesService.getCategoryById(999)).thenReturn(null);  // No se encuentra la categoría

        // Simulamos que el servicio intenta actualizar pero no lo encuentra
        when(categoriesService.updateCategory(999, updatedCategory)).thenThrow(new CategoryException("Error updating category"));

        // Se espera que se lance una excepción de tipo CategoryException
        CategoryException exception = assertThrows(CategoryException.class, () -> {
            categoriesController.updateCategory(999, updatedCategory);
        });

        // Verificamos el mensaje de la excepción
        assertEquals("Error updating category", exception.getMessage());
    }

    @Test
    @DisplayName("Borrar categoría válida")
    public void deleteCategoryWithValidId() {
        // Simulamos que la categoría existe
        when(categoriesService.getCategoryById(validCategory.getIdCategory())).thenReturn(validCategory);

        // Llamamos al método del controlador para borrar la categoría
        ResponseEntity<DeleteResponse> response = categoriesController.deleteCategory(validCategory.getIdCategory());

        // Verificamos que la respuesta devuelva NO_CONTENT (204)
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Borrar categoría inexistente")
    public void deleteCategoryNotFound() {
        // Simulamos que al buscar la categoría con ID 999 no se encuentra (retorna null)
        when(categoriesService.getCategoryById(999)).thenReturn(null);

        // Se espera que se lance una excepción de tipo CategoryException
        CategoryException exception = assertThrows(CategoryException.class, () -> {
            categoriesController.deleteCategory(999);  // Intentamos borrar una categoría inexistente
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("No category found with the specified ID", exception.getMessage());
    }


}
