package es.proyecto.app.mapper;

import es.proyecto.app.entity.CategoryEntity;
import es.proyecto.app.entity.SubcategoryEntity;
import es.swagger.codegen.models.Subcategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-05T17:35:05+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class SubcategoryMapperImpl implements SubcategoryMapper {

    @Override
    public Subcategory toApiDomain(SubcategoryEntity source) {
        if ( source == null ) {
            return null;
        }

        Subcategory subcategory = new Subcategory();

        subcategory.setIdCategory( sourceCategoryIdCategory( source ) );
        subcategory.setIdSubcategory( source.getIdSubcategory() );
        subcategory.setName( source.getName() );
        subcategory.setDescription( source.getDescription() );

        return subcategory;
    }

    @Override
    public List<Subcategory> toApiDomain(List<SubcategoryEntity> source) {
        if ( source == null ) {
            return null;
        }

        List<Subcategory> list = new ArrayList<Subcategory>( source.size() );
        for ( SubcategoryEntity subcategoryEntity : source ) {
            list.add( toApiDomain( subcategoryEntity ) );
        }

        return list;
    }

    @Override
    public SubcategoryEntity toEntity(Subcategory source) {
        if ( source == null ) {
            return null;
        }

        SubcategoryEntity.SubcategoryEntityBuilder subcategoryEntity = SubcategoryEntity.builder();

        subcategoryEntity.category( subcategoryToCategoryEntity( source ) );
        subcategoryEntity.idSubcategory( source.getIdSubcategory() );
        subcategoryEntity.name( source.getName() );
        subcategoryEntity.description( source.getDescription() );

        return subcategoryEntity.build();
    }

    private Integer sourceCategoryIdCategory(SubcategoryEntity subcategoryEntity) {
        if ( subcategoryEntity == null ) {
            return null;
        }
        CategoryEntity category = subcategoryEntity.getCategory();
        if ( category == null ) {
            return null;
        }
        Integer idCategory = category.getIdCategory();
        if ( idCategory == null ) {
            return null;
        }
        return idCategory;
    }

    protected CategoryEntity subcategoryToCategoryEntity(Subcategory subcategory) {
        if ( subcategory == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        categoryEntity.idCategory( subcategory.getIdCategory() );

        return categoryEntity.build();
    }
}
