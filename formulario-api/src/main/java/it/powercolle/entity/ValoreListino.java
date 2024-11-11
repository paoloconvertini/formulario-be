package it.powercolle.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "VALORE_LISTINO")
public class ValoreListino extends PanacheEntity {

    @Column
    public Double valore;
}
