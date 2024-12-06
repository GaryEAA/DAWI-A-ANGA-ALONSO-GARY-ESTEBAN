package pe.edu.cibertec.backoffice_mvc_s.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmId;
    private String title;
    private String description;
    private Integer releaseYear;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;
    @ManyToOne
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;
    private Integer rentalDuration;
    private Double rentalRate;
    private Integer length;
    private Double replacementCost;
    private String rating;
    private String specialFeatures;
    private Date lastUpdate;
    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    private List<FilmActor> filmActors;
    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    private List<FilmCategory> filmCategories;
    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    private List<Inventory> inventories;
}
