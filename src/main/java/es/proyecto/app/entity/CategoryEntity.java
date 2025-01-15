package es.proyecto.app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Validated
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Integer idCategory;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER ,  cascade = CascadeType.ALL)
    private List<SubcategoryEntity> subcategories;




}
