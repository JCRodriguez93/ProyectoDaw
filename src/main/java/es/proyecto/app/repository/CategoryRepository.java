package es.proyecto.app.repository;

import es.proyecto.app.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {


    @Query("SELECT c FROM CategoryEntity c WHERE c.name = :name")
    Optional<CategoryEntity> findCategoryByName(@Param("name") String name);


}
