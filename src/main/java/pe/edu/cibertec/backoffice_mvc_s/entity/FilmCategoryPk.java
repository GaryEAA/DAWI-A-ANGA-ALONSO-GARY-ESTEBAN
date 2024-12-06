package pe.edu.cibertec.backoffice_mvc_s.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FilmCategoryPk {
    @Column(name = "film_id")
    private Integer filmId;
    @Column(name = "category_id")
    private Integer categoryId;
}
