package it.powercolle.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MATERIA_PRIMA_REGISTRO")
public class MateriaPrimaRegistro extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_materia_prima")
    public MateriaPrima materiaPrima;

    @Column
    public Double prezzo;

    @Column(name = "sys_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime updateDate;

    @Column(name = "sys_update_user")
    public String updateUser;


}
