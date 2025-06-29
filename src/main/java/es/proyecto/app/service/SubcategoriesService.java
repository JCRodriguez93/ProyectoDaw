package es.proyecto.app.service;

import es.proyecto.app.entity.SubcategoryEntity;
import es.proyecto.app.mapper.SubcategoryMapper;
import es.proyecto.app.repository.SubcategoryRepository;
import es.swagger.codegen.models.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de subcategorías en el sistema.
 */
@Service
public class SubcategoriesService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    private final SubcategoryMapper mapper = SubcategoryMapper.INSTANCE;

    /**
     * Obtiene todas las subcategorías registradas.
     *
     * @return lista de subcategorías convertidas a modelo API.
     */
    public List<Subcategory> getAllSubcategories() {
        List<SubcategoryEntity> subcategories = subcategoryRepository.findAll();
        return mapper.toApiDomain(subcategories);
    }


    /**
     * Obtiene una subcategoría por su identificador.
     *
     * @param idSubcategory identificador de la subcategoría.
     * @return objeto {@link Subcategory} si existe, o {@code null} si no se encuentra.
     */
    public Subcategory getSubcategoryById(Integer idSubcategory) {
        Optional<SubcategoryEntity> optionalSubcategoryEntity = subcategoryRepository.findById(idSubcategory);
        return optionalSubcategoryEntity.map(mapper::toApiDomain).orElse(null);
    }

    /**
     * Crea una nueva subcategoría.
     *
     * @param subcategory objeto {@link Subcategory} con los datos de la subcategoría a crear.
     */
    public void createSubcategory(Subcategory subcategory) {
        SubcategoryEntity entity = mapper.toEntity(subcategory);
        subcategoryRepository.save(entity);
    }

    /**
     * Actualiza una subcategoría existente.
     *
     * @param idSubcategory identificador de la subcategoría a actualizar.
     * @param subcategory   objeto {@link Subcategory} con los nuevos datos.
     * @return {@link HttpStatus#OK} si se actualizó correctamente,
     *         {@link HttpStatus#NOT_FOUND} si la subcategoría no existe.
     */
    public HttpStatus updateSubcategory(Integer idSubcategory, Subcategory subcategory) {
        Optional<SubcategoryEntity> existingSubcategory = subcategoryRepository.findById(idSubcategory);
        if (existingSubcategory.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        subcategory.setIdCategory(idSubcategory);
        subcategoryRepository.save(mapper.toEntity(subcategory));
        return HttpStatus.OK;
    }

    /**
     * Elimina una subcategoría por su identificador.
     *
     * @param idSubcategory identificador de la subcategoría a eliminar.
     */
    public void deleteSubcategory(Integer idSubcategory) {
        if (subcategoryRepository.existsById(idSubcategory)) {
            subcategoryRepository.deleteById(idSubcategory);
        }
    }

    /**
     * Verifica si existe una subcategoría con el mismo nombre dentro de una categoría.
     *
     * @param name       nombre de la subcategoría.
     * @param idCategory identificador de la categoría.
     * @return {@code true} si existe, {@code false} en caso contrario.
     */
    public boolean existsByNameAndCategory(String name, Integer idCategory) {
        return subcategoryRepository.existsByNameAndCategory(name, idCategory);
    }
}
