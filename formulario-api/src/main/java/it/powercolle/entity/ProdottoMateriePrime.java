package it.powercolle.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "PRODOTTO_MATERIE_PRIME")
@IdClass(ProdottoMateriePrimeId.class)
public class ProdottoMateriePrime extends PanacheEntityBase {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_prodotto", referencedColumnName = "id")
    public Prodotto prodotto;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_materia_prima", referencedColumnName = "id")
    public MateriaPrima materiaPrima;

    @Column
    public Double percentuale;
}
