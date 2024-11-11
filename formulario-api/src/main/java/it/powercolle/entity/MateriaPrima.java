package it.powercolle.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "MATERIA_PRIMA")
public class MateriaPrima extends PanacheEntity {

    @Column(length = 300)
    public String nome;

    @Column(name = "unita_misura", length = 2)
    public String unitaMisura;

    @Column
    public Double prezzo;

    @Column(length = 2)
    public String tipologia;
}
