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
public class FilmActorPk {
    @Column(name = "actor_id")
    private Integer actorId;
    @Column(name = "film_id")
    private Integer filmId;
}
