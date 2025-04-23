package it.powercolle.entity.weber;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "MATERIA_PRIMA_WEBER")
public class MateriaPrimaWeber extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    @Column(length = 300)
    public String nome;

    @Column(name = "unita_misura", length = 2)
    public String unitaMisura;

    @Column
    public Double prezzo;

    @Column(length = 2)
    public String tipologia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
