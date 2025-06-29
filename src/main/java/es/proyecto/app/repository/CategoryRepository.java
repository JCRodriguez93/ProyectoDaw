package es.proyecto.app.repository;

import es.proyecto.app.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repositorio para gestionar la persistencia de {@link CategoryEntity}.
 * <p>
 * Extiende JpaRepository para proveer operaciones CRUD estándar y define
 * consultas personalizadas para buscar categorías por nombre.
 * </p>
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    /**
     * Busca una categoría por su nombre único.
     *
     * @param name nombre de la categoría a buscar.
     * @return Optional que contiene la categoría si existe, o vacío si no.
     */
    @Query("SELECT c FROM CategoryEntity c WHERE c.name = :name")
    Optional<CategoryEntity> findCategoryByName(@Param("name") String name);


}
