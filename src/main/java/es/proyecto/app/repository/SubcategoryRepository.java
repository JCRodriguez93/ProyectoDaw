package es.proyecto.app.repository;

import es.proyecto.app.entity.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Integer> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM SubcategoryEntity s WHERE s.name = :name AND s.category.idCategory = :idCategory")
    boolean existsByNameAndCategory(@Param("name") String name, @Param("idCategory") Integer idCategory);
}
