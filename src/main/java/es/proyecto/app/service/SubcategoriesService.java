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

@Service
public class SubcategoriesService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    private final SubcategoryMapper mapper = SubcategoryMapper.INSTANCE;

    public List<Subcategory> getAllSubcategories() {
        List<SubcategoryEntity> subcategories = subcategoryRepository.findAll();
        return mapper.toApiDomain(subcategories);
    }

    public Subcategory getSubcategoryById(Integer idSubcategory) {
        Optional<SubcategoryEntity> optionalSubcategoryEntity = subcategoryRepository.findById(idSubcategory);
        return optionalSubcategoryEntity.map(mapper::toApiDomain).orElse(null);
    }

    public void createSubcategory(Subcategory subcategory) {
        SubcategoryEntity entity = mapper.toEntity(subcategory);
        subcategoryRepository.save(entity);
    }

    public HttpStatus updateSubcategory(Integer idSubcategory, Subcategory subcategory) {
        Optional<SubcategoryEntity> existingSubcategory = subcategoryRepository.findById(idSubcategory);
        if (existingSubcategory.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        subcategory.setIdCategory(idSubcategory);
        subcategoryRepository.save(mapper.toEntity(subcategory));
        return HttpStatus.OK;
    }

    public boolean deleteSubcategory(Integer idSubcategory) {
        if (subcategoryRepository.existsById(idSubcategory)) {
            subcategoryRepository.deleteById(idSubcategory);
            return true;
        } else {
            return false;
        }
    }

    public boolean existsByNameAndCategory(String name, Integer idCategory) {
        return subcategoryRepository.existsByNameAndCategory(name, idCategory);
    }
}
