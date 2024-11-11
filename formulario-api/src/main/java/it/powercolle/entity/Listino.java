package it.powercolle.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "LISTINO",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id_prodotto", "id_valore_listino"})})
public class Listino extends PanacheEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prodotto")
    public Prodotto prodotto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valore_listino")
    public ValoreListino valoreListino;

    @Column(name = "ricavo")
    public Double ricavo;
}
