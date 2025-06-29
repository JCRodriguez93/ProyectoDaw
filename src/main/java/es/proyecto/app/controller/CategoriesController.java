package es.proyecto.app.controller;

import es.proyecto.app.error.CategoryException;
import es.proyecto.app.service.CategoriesService;
import es.swagger.codegen.api.CategoryApi;
import es.swagger.codegen.models.CategoriesResponse;
import es.swagger.codegen.models.Category;
import es.swagger.codegen.models.DeleteResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controlador REST responsable de gestionar las operaciones relacionadas con las categorías.
 *
 * <p>Implementa la interfaz {@link CategoryApi} generada automáticamente mediante Swagger Codegen.
 * Expone endpoints para crear, recuperar, actualizar y eliminar categorías dentro del sistema.
 * Las respuestas están diseñadas conforme a los códigos de estado HTTP estándar, con validaciones
 * detalladas y excepciones personalizadas a través de {@link CategoryException}.</p>
 *
 * <p>Este controlador utiliza el servicio {@link CategoriesService} para acceder a la lógica
 * de negocio. Además, hace uso de la librería SLF4J para el registro de logs de auditoría
 * y errores.</p>
 *
 * <p>Todos los endpoints están documentados siguiendo buenas prácticas REST y validan exhaustivamente
 * la entrada del cliente antes de realizar operaciones sobre la base de datos.</p>
 *
 * @author Jorge
 */
@Slf4j
@RestController
public class CategoriesController implements CategoryApi {

    @Autowired
    private CategoriesService categoriesService;

    private static final Logger logger = LoggerFactory.getLogger(CategoriesController.class);


    /**
     * Crea una nueva categoría en el sistema.
     * <p>
     * Valida que el cuerpo recibido no sea nulo, que el nombre de la categoría esté presente
     * y que no exista ya una categoría con el mismo nombre. Si todas las validaciones se superan,
     * la categoría se crea mediante el servicio correspondiente.
     *
     * @param body el objeto {@link Category} que contiene los datos de la categoría a crear.
     * @return una {@link ResponseEntity} con el estado:
     * <ul>
     *     <li>{@code 201 Created} si la categoría fue creada correctamente.</li>
     * </ul>
     * @throws CategoryException si:
     * <ul>
     *     <li>El cuerpo es nulo ({@link CategoryException#NULL_BODY_EXCEPTION}).</li>
     *     <li>El nombre de la categoría es nulo o vacío ({@link CategoryException#MISSING_CATEGORY_NAME_EXCEPTION}).</li>
     *     <li>Ya existe una categoría con el mismo nombre ({@link CategoryException#CATEGORY_ALREADY_EXISTS_EXCEPTION}).</li>
     * </ul>
     */
    @Override
    public ResponseEntity<Category> createCategory(Category body) {
        if (body == null) {
            logger.error("Null body provided");
            throw CategoryException.NULL_BODY_EXCEPTION;
        }

        if (body.getName() == null || body.getName().isEmpty()) {
            logger.error("Invalid category name");
            throw CategoryException.MISSING_CATEGORY_NAME_EXCEPTION;
        }

        if (categoriesService.getCategoryByName(body.getName())) {
            logger.error("Category already exists with name: {}", body.getName());
            throw CategoryException.CATEGORY_ALREADY_EXISTS_EXCEPTION;
        }
        categoriesService.createCategory(body);

        logger.info("Category created successfully: {}", body.getIdCategory());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Elimina una categoría existente por su ID.
     * <p>
     * Este método valida que el ID proporcionado tenga un formato correcto, verifica que la
     * categoría exista en la base de datos y procede a eliminarla si todo es correcto.
     * Si ocurre un error durante la eliminación, lanza una excepción personalizada.
     *
     * @param idCategory el identificador de la categoría a eliminar.
     * @return una {@link ResponseEntity} con estado:
     * <ul>
     *     <li>{@code 204 No Content} si la categoría fue eliminada correctamente.</li>
     * </ul>
     * @throws CategoryException si:
     * <ul>
     *     <li>El ID es inválido ({@link CategoryException#INVALID_CATEGORY_ID_EXCEPTION}).</li>
     *     <li>No se encuentra la categoría ({@link CategoryException#NO_CATEGORY_FOUND_EXCEPTION}).</li>
     *     <li>Ocurre un error durante la eliminación (excepción personalizada con mensaje detallado).</li>
     * </ul>
     */
    @Override
    public ResponseEntity<DeleteResponse> deleteCategory(Integer idCategory) {
        if (!isValidId(String.valueOf(idCategory))) {
            logger.error("Invalid category ID: {} format ", idCategory);
            throw CategoryException.INVALID_CATEGORY_ID_EXCEPTION;
        }

        Category deleteCategory = categoriesService.getCategoryById(idCategory);

        if (deleteCategory == null) {
            logger.error("Category with id {} not found", idCategory);
            throw CategoryException.NO_CATEGORY_FOUND_EXCEPTION;
        }

        try {
            categoriesService.deleteCategory(idCategory);
            logger.info("Category with id {} deleted successfully", idCategory);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting category with id {}: {}", idCategory, e.getMessage());
            throw new CategoryException("Error deleting category with id " + idCategory);
        }
    }

    /**
     * Recupera todas las categorías disponibles en el sistema.
     * <p>
     * Este método obtiene la lista de categorías desde el servicio correspondiente.
     * Si no se encuentra ninguna categoría, lanza una excepción personalizada.
     * Si ocurre un error durante el proceso, devuelve un estado de error interno.
     *
     * @return una {@link ResponseEntity} que contiene:
     * <ul>
     *     <li>Un objeto {@link CategoriesResponse} con la lista de categorías y código {@code 200 OK}, si se recuperan correctamente.</li>
     *     <li>Código {@code 500 Internal Server Error}, si ocurre un fallo durante la operación.</li>
     * </ul>
     *
     * @throws CategoryException si no se encuentran categorías disponibles
     *         ({@link CategoryException#NO_CATEGORY_FOUND_EXCEPTION}).
     */
    @Override
    public ResponseEntity<CategoriesResponse> getCategories() {
        try {
            List<Category> categoryList = categoriesService.getAllCategories();
            if (categoryList.isEmpty()) {
                throw CategoryException.NO_CATEGORY_FOUND_EXCEPTION;
            }
            CategoriesResponse response = new CategoriesResponse();
            response.setCategories(categoryList);
            logger.info("Successfully retrieved categories");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CategoryException e) {
            logger.error("Error retrieving categories: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Recupera una categoría específica por su ID.
     * <p>
     * Este método valida que el ID proporcionado tenga el formato correcto y, si es válido,
     * consulta el servicio para obtener la categoría correspondiente. Si la categoría no se
     * encuentra, devuelve un código 404. En caso de error interno, devuelve un código 500.
     *
     * @param idCategory el identificador numérico de la categoría a recuperar.
     * @return una {@link ResponseEntity} que contiene:
     * <ul>
     *     <li>Un objeto {@link Category} con código {@code 200 OK} si se encuentra la categoría.</li>
     *     <li>{@code 400 Bad Request} si el ID es inválido.</li>
     *     <li>{@code 404 Not Found} si no se encuentra ninguna categoría con ese ID.</li>
     *     <li>{@code 500 Internal Server Error} si ocurre un error inesperado durante la operación.</li>
     * </ul>
     */
    @Override
    public ResponseEntity<Category> getCategoryById(Integer idCategory) {
        try {
            if (!isValidId(String.valueOf(idCategory))) {
                logger.error("Invalid category ID: {} format: ", idCategory);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Category category = categoriesService.getCategoryById(idCategory);

            if (category == null) {
                logger.info("Category with id {} not found", idCategory);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                logger.info("Category with id {} retrieved successfully", idCategory);
                return new ResponseEntity<>(category, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error retrieving category with id {}, message: {}", idCategory, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza una categoría existente utilizando el ID proporcionado y los datos del cuerpo de la petición.
     * <p>
     * Este método realiza varias validaciones:
     * <ul>
     *     <li>Verifica que el cuerpo de la petición no sea nulo.</li>
     *     <li>Comprueba que se proporcione un ID de categoría en el objeto.</li>
     *     <li>Valida que la categoría con el ID especificado exista en la base de datos.</li>
     * </ul>
     * Si todas las validaciones son correctas, se actualiza la categoría y se devuelve la respuesta con el estado HTTP correspondiente.
     *
     * @param idCategory el ID de la categoría que se desea actualizar.
     * @param body el objeto {@link Category} con los nuevos datos a aplicar.
     * @return una {@link ResponseEntity} vacía con el código de estado:
     * <ul>
     *     <li>{@code 200 OK} si la actualización fue exitosa.</li>
     *     <li>{@code 400 Bad Request} si el cuerpo es nulo o el ID es inválido.</li>
     *     <li>{@code 404 Not Found} si no se encuentra la categoría con el ID dado.</li>
     *     <li>{@code 500 Internal Server Error} si ocurre un error inesperado.</li>
     * </ul>
     * @throws CategoryException si hay problemas de validación o errores durante la operación de actualización.
     */
    @Override
    public ResponseEntity<Void> updateCategory(Integer idCategory, Category body) {
        try {
            if (body == null) {
                logger.error("Error updating category. Null body provided");
                throw CategoryException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdCategory() == null) {
                logger.error("Category ID is required for updating");
                throw CategoryException.MISSING_CATEGORY_ID_EXCEPTION;
            }

            Category existingCategory = categoriesService.getCategoryById(idCategory);
            if (existingCategory == null) {
                logger.error("No category found with ID {}", idCategory);
                throw CategoryException.NO_CATEGORY_FOUND_EXCEPTION;
            }
            HttpStatus status = categoriesService.updateCategory(idCategory, body);
            logger.info("Category with id {} updated successfully", idCategory);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            logger.error("Invalid ID format: {}", idCategory);
            throw CategoryException.INVALID_CATEGORY_ID_EXCEPTION;
        } catch (Exception e) {
            logger.error("Error updating category with id {}. Exception message: {}", idCategory, e.getMessage());
            throw CategoryException.ERROR_UPDATING_CATEGORY_EXCEPTION;
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
            return false;
        }
    }
}
