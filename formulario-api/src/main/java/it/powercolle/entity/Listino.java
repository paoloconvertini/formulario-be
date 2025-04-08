package it.powercolle.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "LISTINO",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id_prodotto", "id_valore_listino"})})
public class Listino extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prodotto")
    public Prodotto prodotto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valore_listino")
    public ValoreListino valoreListino;

    @Column(name = "ricavo")
    public Double ricavo;
}
