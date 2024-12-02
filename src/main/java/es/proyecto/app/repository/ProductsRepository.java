package es.proyecto.app.repository;

import es.proyecto.app.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Integer> {

    // Consulta personalizada para encontrar un producto por su nombre
    @Query("SELECT p FROM ProductsEntity p WHERE p.name = ?1")
    Optional<ProductsEntity> findByName(String name);

    // Consulta personalizada para obtener todos los productos de una subcategoría
    @Query("SELECT p FROM ProductsEntity p WHERE p.subcategory.idSubcategory = ?1")
    List<ProductsEntity> findBySubcategoryId(int subcategoryId);

    // Consulta personalizada para obtener todos los productos cuyo precio sea menor que el valor especificado
    @Query("SELECT p FROM ProductsEntity p WHERE p.price < ?1")
    List<ProductsEntity> findProductsByPriceLessThan(double price);
}
