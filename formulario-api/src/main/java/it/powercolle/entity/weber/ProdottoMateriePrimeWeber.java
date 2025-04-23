package it.powercolle.entity.weber;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.powercolle.entity.ProdottoMateriePrimeId;
import jakarta.persistence.*;

@Entity
@Table(name = "PRODOTTO_MATERIE_PRIME_WEBER")
@IdClass(ProdottoMateriePrimeId.class)
public class ProdottoMateriePrimeWeber extends PanacheEntityBase {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_prodotto", referencedColumnName = "id")
    public ProdottoWeber prodotto;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_materia_prima", referencedColumnName = "id")
    public MateriaPrimaWeber materiaPrima;

    @Column
    public Double percentuale;
}
