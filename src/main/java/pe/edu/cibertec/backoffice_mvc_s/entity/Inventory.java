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
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "film_id")
    private Film film;

    private Integer storeId;
    private Date lastUpdate;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.REMOVE)
    private List<Rental> rentals;
}
