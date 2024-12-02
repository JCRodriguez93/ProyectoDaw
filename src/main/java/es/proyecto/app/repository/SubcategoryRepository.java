package es.proyecto.app.repository;

import es.proyecto.app.entity.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Integer> {

    // Consulta personalizada para encontrar una subcategoría por su nombre
    @Query("SELECT s FROM SubcategoryEntity s WHERE s.name = ?1")
    Optional<SubcategoryEntity> findByName(String name);

    // Consulta personalizada para obtener todas las subcategorías de una categoría
    @Query("SELECT s FROM SubcategoryEntity s WHERE s.category.idCategory = ?1")
    List<SubcategoryEntity> findByCategoryId(int categoryId);
}
