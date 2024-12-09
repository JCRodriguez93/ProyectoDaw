package es.proyecto.app.service;

import es.proyecto.app.entity.SubcategoryEntity;
import es.proyecto.app.mapper.SubcategoryMapper;
import es.proyecto.app.repository.SubcategoryRepository;
import es.swagger.codegen.models.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoriesService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    private final SubcategoryMapper mapper = SubcategoryMapper.INSTANCE;

    public List<Subcategory> getAllSubcategories() {
        List<SubcategoryEntity> subcategories = subcategoryRepository.findAll();
        return mapper.toApiDomain(subcategories);
    }
}
