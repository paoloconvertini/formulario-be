package it.powercolle.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIPO_PRODOTTO")
public class TipoProdotto extends PanacheEntity {

    @Column
    public String descrizione;
}
