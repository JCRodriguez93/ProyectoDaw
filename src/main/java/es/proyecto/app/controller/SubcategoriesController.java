package es.proyecto.app.controller;


import es.proyecto.app.error.SubcategoryException;
import es.proyecto.app.service.SubcategoriesService;
import es.swagger.codegen.api.SubcategoryApi;
import es.swagger.codegen.models.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controlador REST para la gestión de subcategorías.
 * <p>
 * Esta clase implementa la interfaz {@link SubcategoryApi} generada por Swagger Codegen y
 * define los endpoints para crear, eliminar, obtener y actualizar subcategorías.
 * <p>
 * Se encarga de validar las solicitudes, controlar errores mediante excepciones específicas
 * y delegar la lógica de negocio al servicio {@link SubcategoriesService}.
 * <p>
 * Además, registra eventos relevantes y errores mediante el sistema de logging {@code SLF4J}.
 *
 * <h2>Operaciones soportadas:</h2>
 * <ul>
 *     <li>Creación de nuevas subcategorías.</li>
 *     <li>Eliminación de subcategorías por ID.</li>
 *     <li>Obtención de todas las subcategorías o una específica por ID.</li>
 *     <li>Actualización de subcategorías existentes.</li>
 * </ul>
 *
 * <p>Las respuestas HTTP se ajustan a los estándares REST, utilizando códigos como:
 * <ul>
 *     <li>201 (CREATED) al crear.</li>
 *     <li>204 (NO_CONTENT) al eliminar.</li>
 *     <li>200 (OK) al obtener datos correctamente.</li>
 *     <li>400 (BAD_REQUEST) en caso de parámetros inválidos.</li>
 *     <li>404 (NOT_FOUND) si no existe la subcategoría solicitada.</li>
 *     <li>500 (INTERNAL_SERVER_ERROR) para errores inesperados.</li>
 * </ul>
 *
 * <p>Las validaciones específicas, como evitar nombres duplicados dentro de la misma categoría,
 * se realizan antes de delegar en el servicio.
 *
 * @see SubcategoriesService
 * @see SubcategoryApi
 * @see es.proyecto.app.error.SubcategoryException
 */
@Slf4j
@RestController
public class SubcategoriesController implements SubcategoryApi {

    @Autowired
    private SubcategoriesService subcategoriesService;

    private static final Logger logger = LoggerFactory.getLogger(SubcategoriesController.class);

    /**
     * Crea una nueva subcategoría en la base de datos.
     * <p>
     * Valida que el cuerpo de la petición no sea nulo y que el nombre de la subcategoría esté presente y no vacío.
     * Además, comprueba que no exista una subcategoría con el mismo nombre en la misma categoría para evitar duplicados.
     * Si alguna validación falla, lanza la excepción correspondiente de {@link SubcategoryException}.
     * En caso de éxito, invoca el servicio para crear la subcategoría y devuelve un estado HTTP 201 (CREATED).
     *
     * @param body el objeto {@link Subcategory} con los datos de la nueva subcategoría
     * @return un {@link ResponseEntity} con código HTTP 201 (CREATED) si la subcategoría se crea correctamente
     * @throws SubcategoryException si el cuerpo es nulo, el nombre es inválido o ya existe una subcategoría con el mismo nombre en la categoría
     */
    @Override
    public ResponseEntity<Subcategory> createSubcategory(Subcategory body) {
        if (body == null) {
            logger.error("Null body provided");
            throw SubcategoryException.NULL_BODY_EXCEPTION;
        }

        if (body.getName() == null || body.getName().isEmpty()) {
            logger.error("Invalid subcategory name ");
            throw SubcategoryException.MISSING_SUBCATEGORY_NAME_EXCEPTION;
        }

        // Comprobación de no hacer una subcategoría con nombre repetido
        if (subcategoriesService.existsByNameAndCategory(body.getName(), body.getIdCategory())) {
            logger.error("Duplicate subcategory name");
            throw SubcategoryException.DUPLICATE_SUBCATEGORY_NAME_EXCEPTION;
        }

        subcategoriesService.createSubcategory(body);
        logger.info("Subcategory created successfully: {}", body.getIdCategory());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Elimina una subcategoría identificada por su ID.
     * <p>
     * Valida que el ID proporcionado sea válido. Si no es válido, lanza {@link SubcategoryException#INVALID_SUBCATEGORY_ID_EXCEPTION}.
     * Verifica que la subcategoría con el ID dado exista; si no, lanza {@link SubcategoryException#NO_SUBCATEGORY_FOUND_EXCEPTION}.
     * Intenta eliminar la subcategoría mediante el servicio correspondiente.
     * En caso de éxito, devuelve un {@code ResponseEntity} con estado HTTP 204 (NO_CONTENT).
     * Si ocurre algún error durante la eliminación, lanza una {@link SubcategoryException} con el mensaje correspondiente.
     *
     * @param idSubcategory el identificador de la subcategoría a eliminar
     * @return un {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa
     * @throws SubcategoryException si el ID es inválido, no se encuentra la subcategoría, o hay un error durante la eliminación
     */
    @Override
    public ResponseEntity<Void> deleteSubcategory(Integer idSubcategory) {
        if (!isValidId(String.valueOf(idSubcategory))) {
            logger.error("Can't delete. Invalid subcategory ID: {}", idSubcategory);
            throw SubcategoryException.INVALID_SUBCATEGORY_ID_EXCEPTION;
        }

        Subcategory deleteSubcategory = subcategoriesService.getSubcategoryById(idSubcategory);

        if (deleteSubcategory == null) {
            logger.error("Subcategory with id {} not found", idSubcategory);
            throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
        }

        try {
            subcategoriesService.deleteSubcategory(idSubcategory);
            logger.info("Subcategory with id {} deleted successfully", idSubcategory);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting subcategory with id {}. Message: {}", idSubcategory, e.getMessage());
            throw new SubcategoryException("Error deleting subcategory with id " + idSubcategory);
        }
    }

    /**
     * Obtiene todas las subcategorías disponibles.
     * <p>
     * Llama al servicio para recuperar la lista completa de subcategorías.
     * Si la lista está vacía, lanza {@link SubcategoryException#NO_SUBCATEGORY_FOUND_EXCEPTION}.
     * En caso de éxito, devuelve un {@link ResponseEntity} con la lista de subcategorías y código HTTP 200 (OK).
     * Si ocurre algún error, captura la excepción y devuelve un estado HTTP 500 (INTERNAL_SERVER_ERROR).
     *
     * @return un {@link ResponseEntity} que contiene un objeto {@link SubcategoriesResponse} con la lista de subcategorías,
     *         o un código HTTP 500 si hay un error.
     */
    @Override
    public ResponseEntity<SubcategoriesResponse> getSubcategories() {
        try {
            List<Subcategory> subcategoryList = subcategoriesService.getAllSubcategories();

            if (subcategoryList.isEmpty()) {
                logger.error("No subcategories found");
                throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
            }

            SubcategoriesResponse response = new SubcategoriesResponse();
            response.setSubcategories(subcategoryList);
            logger.info("Successfully fetched all subcategories");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SubcategoryException e) {
            logger.error("Error fetching all subcategories: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene una subcategoría específica por su ID.
     * <p>
     * Valida el formato del ID; si es inválido, devuelve un estado HTTP 400 (BAD_REQUEST).
     * Intenta recuperar la subcategoría desde el servicio.
     * Si no se encuentra, devuelve un estado HTTP 404 (NOT_FOUND).
     * En caso de éxito, devuelve la subcategoría con estado HTTP 200 (OK).
     * Si ocurre cualquier error durante la operación, devuelve un estado HTTP 500 (INTERNAL_SERVER_ERROR).
     *
     * @param idSubcategory el identificador de la subcategoría a obtener
     * @return un {@link ResponseEntity} que contiene la subcategoría solicitada o el código de estado HTTP correspondiente
     */
    @Override
    public ResponseEntity<Subcategory> getSubcategoryById(Integer idSubcategory) {
        try {
            if (!isValidId(String.valueOf(idSubcategory))) {
                logger.error("Invalid subcategory ID format: {}", idSubcategory);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Subcategory subcategory = subcategoriesService.getSubcategoryById(idSubcategory);

            if (subcategory == null) {
                logger.info("Subcategory with id {} not found", idSubcategory);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("Subcategory with id {} retrieved successfully", idSubcategory);
                return new ResponseEntity<>(subcategory, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error retrieving subcategory with id {}. Message: {}", idSubcategory, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza una subcategoría existente con los datos proporcionados.
     * <p>
     * Verifica que el cuerpo de la petición no sea nulo y que incluya el ID de la categoría.
     * Busca la subcategoría existente por el ID proporcionado; si no existe, lanza excepción.
     * Llama al servicio para actualizar la subcategoría y devuelve el código HTTP correspondiente.
     * Captura excepciones específicas como formato inválido del ID y errores generales,
     * lanzando excepciones personalizadas según el caso.
     *
     * @param idSubcategory el ID de la subcategoría a actualizar
     * @param body el objeto {@link Subcategory} con los nuevos datos para actualizar
     * @return un {@link ResponseEntity} con el estado HTTP resultante de la operación
     * @throws SubcategoryException si el cuerpo es nulo, falta el ID, la subcategoría no existe,
     *                              el ID es inválido o se produce un error al actualizar
     */
    @Override
    public ResponseEntity<Subcategory> updateSubcategory(Integer idSubcategory, Subcategory body) {
        try {
            if (body == null) {
                logger.error("Cannot update subcategory. Null body provided");
                throw SubcategoryException.NULL_BODY_EXCEPTION;
            }
            if (body.getIdCategory() == null) {
                logger.error("Subcategory ID is required for updating");
                throw SubcategoryException.MISSING_SUBCATEGORY_ID_EXCEPTION;
            }

            Subcategory existingSubcategory = subcategoriesService.getSubcategoryById(idSubcategory);
            if (existingSubcategory == null) {
                logger.error("No subcategory found with ID {}", idSubcategory);
                throw SubcategoryException.NO_SUBCATEGORY_FOUND_EXCEPTION;
            }
            HttpStatus status = subcategoriesService.updateSubcategory(idSubcategory, body);
            logger.info("Subcategory with id {} updated successfully", idSubcategory);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            logger.error("Invalid ID format: {}", idSubcategory);
            throw SubcategoryException.INVALID_SUBCATEGORY_ID_EXCEPTION;
        } catch (Exception e) {
            logger.error("Error updating subcategory with id {}. Message: {}", idSubcategory, e.getMessage());
            throw SubcategoryException.ERROR_UPDATING_SUBCATEGORY_EXCEPTION;
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
