package es.proyecto.app.mapper;

import es.proyecto.app.entity.CategoryEntity;
import es.proyecto.app.entity.SubcategoryEntity;
import es.swagger.codegen.models.Category;
import es.swagger.codegen.models.Subcategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-05T17:35:05+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Autowired
    private SubcategoryMapper subcategoryMapper;

    @Override
    public Category toApiDomain(CategoryEntity source) {
        if ( source == null ) {
            return null;
        }

        Category category = new Category();

        category.setIdCategory( source.getIdCategory() );
        category.setName( source.getName() );
        category.setDescription( source.getDescription() );
        category.setSubcategories( subcategoryMapper.toApiDomain( source.getSubcategories() ) );

        return category;
    }

    @Override
    public List<Category> toApiDomain(List<CategoryEntity> source) {
        if ( source == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( source.size() );
        for ( CategoryEntity categoryEntity : source ) {
            list.add( toApiDomain( categoryEntity ) );
        }

        return list;
    }

    @Override
    public CategoryEntity toEntity(Category source) {
        if ( source == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        categoryEntity.idCategory( source.getIdCategory() );
        categoryEntity.name( source.getName() );
        categoryEntity.description( source.getDescription() );
        categoryEntity.subcategories( subcategoryListToSubcategoryEntityList( source.getSubcategories() ) );

        return categoryEntity.build();
    }

    protected List<SubcategoryEntity> subcategoryListToSubcategoryEntityList(List<Subcategory> list) {
        if ( list == null ) {
            return null;
        }

        List<SubcategoryEntity> list1 = new ArrayList<SubcategoryEntity>( list.size() );
        for ( Subcategory subcategory : list ) {
            list1.add( subcategoryMapper.toEntity( subcategory ) );
        }

        return list1;
    }
}
