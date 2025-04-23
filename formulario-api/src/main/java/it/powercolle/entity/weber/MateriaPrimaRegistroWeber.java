package it.powercolle.entity.weber;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.powercolle.entity.MateriaPrima;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MATERIA_PRIMA_REGISTRO_WEBER")
public class MateriaPrimaRegistroWeber extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;
    @ManyToOne
    @JoinColumn(name = "id_materia_prima")
    public MateriaPrimaWeber materiaPrima;

    @Column
    public Double prezzo;

    @Column(name = "sys_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime updateDate;

    @Column(name = "sys_update_user")
    public String updateUser;


}
