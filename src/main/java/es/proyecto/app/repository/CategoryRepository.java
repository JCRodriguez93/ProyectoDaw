package es.proyecto.app.repository;

import es.proyecto.app.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    // Consulta personalizada para encontrar una categoría por su nombre
    @Query("SELECT c FROM CategoryEntity c WHERE c.name = ?1")
    Optional<CategoryEntity> findByName(String name);

    // Consulta personalizada para contar el número de categorías
    @Query("SELECT COUNT(c) FROM CategoryEntity c")
    long countCategories();
}
