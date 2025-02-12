package es.proyecto.app.Controller;

import es.proyecto.app.controller.SubcategoriesController;
import es.proyecto.app.error.SubcategoryException;
import es.proyecto.app.service.SubcategoriesService;
import es.swagger.codegen.models.Subcategory;
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
public class SubcategoriesControllerTest {

    @Mock
    private SubcategoriesService subcategoriesService;

    @InjectMocks
    private SubcategoriesController subcategoriesController;

    private Subcategory validSubcategory;
    private Subcategory invalidSubcategory;

    @BeforeEach
    public void setUp() {
        // Inicializamos las subcategorías con la clase Subcategory de Swagger
        validSubcategory = new Subcategory();
        validSubcategory.setName("Piercings");
        validSubcategory.setIdSubcategory(1);

        invalidSubcategory = new Subcategory();
        invalidSubcategory.setName(null);  // Nombre nulo para la prueba de error
        invalidSubcategory.setIdSubcategory(2);
    }

    @Test
    @DisplayName("Crear subcategoría con datos válidos")
    public void createSubcategoryWithValidDataAndThenReturnsCreated() {
        // Simulamos el comportamiento del servicio de creación de subcategorías
        doNothing().when(subcategoriesService).createSubcategory(validSubcategory);

        // Llamamos al método del controlador
        ResponseEntity<Subcategory> response = subcategoriesController.createSubcategory(validSubcategory);

        // Verificamos que la respuesta sea correcta (creación exitosa)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verificamos que el servicio fue llamado con los argumentos esperados
        verify(subcategoriesService, times(1)).createSubcategory(validSubcategory);
    }

    @Test
    @DisplayName("Crear subcategoría con nombre vacío")
    public void createSubcategoryWithEmptyNameAndThenReturnsBadRequest() {
        // Modificamos el nombre para que esté vacío
        invalidSubcategory.setName("");

        // Comprobamos que se lanza la excepción esperada
        SubcategoryException exception = assertThrows(SubcategoryException.class, () -> {
            subcategoriesController.createSubcategory(invalidSubcategory);
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("Subcategory name is required", exception.getMessage());
    }

    @Test
    @DisplayName("Crear subcategoría con nombre nulo")
    public void createSubcategoryWithNullNameAndThenReturnsBadRequest() {
        // Comprobamos que se lanza la excepción esperada
        SubcategoryException exception = assertThrows(SubcategoryException.class, () -> {
            subcategoriesController.createSubcategory(invalidSubcategory);
        });

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("Subcategory name is required", exception.getMessage());
    }
}
