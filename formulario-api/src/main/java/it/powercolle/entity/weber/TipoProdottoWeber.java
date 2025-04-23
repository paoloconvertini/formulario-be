package it.powercolle.entity.weber;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "TIPO_PRODOTTO_WEBER")
public class TipoProdottoWeber extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;
    @Column
    public String descrizione;
}
