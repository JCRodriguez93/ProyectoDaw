package es.proyecto.app.mapper;

import es.proyecto.app.entity.CategoryEntity;
import es.proyecto.app.entity.SubcategoryEntity;
import es.swagger.codegen.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void testMapping() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setIdCategory(1);
        categoryEntity.setName("Test Category");
        categoryEntity.setDescription("Description");

        SubcategoryEntity subcategoryEntity = new SubcategoryEntity();
        subcategoryEntity.setIdSubcategory(1);
        subcategoryEntity.setName("Test Subcategory");
        subcategoryEntity.setDescription("Description");
        subcategoryEntity.setCategory(categoryEntity);

        categoryEntity.setSubcategories(Arrays.asList(subcategoryEntity));

        Category category = categoryMapper.toApiDomain(categoryEntity);

        assertNotNull(category);
        assertEquals(1, category.getIdCategory().intValue());
        assertEquals(1, category.getSubcategories().get(0).getIdCategory().intValue());
    }
}
