package it.powercolle.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "VALORE_LISTINO")
public class ValoreListino extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;
    @Column
    public Double valore;

    @Column(name = "data_validita")
    public LocalDate dataValidita;
}
