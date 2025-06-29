package es.proyecto.app.repository;

import es.proyecto.app.entity.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Repositorio para la gestión de la entidad {@link SubcategoryEntity}.
 * <p>
 * Proporciona métodos CRUD estándar y consultas personalizadas para subcategorías,
 * incluyendo la verificación de existencia de subcategorías por nombre y categoría.
 * </p>
 */
public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Integer> {

    /**
     * Comprueba si existe una subcategoría con el nombre especificado dentro de una categoría determinada.
     *
     * @param name       Nombre de la subcategoría.
     * @param idCategory Identificador de la categoría a la que pertenece la subcategoría.
     * @return {@code true} si existe una subcategoría con ese nombre en la categoría indicada; {@code false} en caso contrario.
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM SubcategoryEntity s WHERE s.name = :name AND s.category.idCategory = :idCategory")
    boolean existsByNameAndCategory(@Param("name") String name, @Param("idCategory") Integer idCategory);
}
