package it.powercolle.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "PRODOTTO", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
public class Prodotto extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 300)
    public String nome;

    @Column
    public Double costo;

    @Column(name="sys_update_date")
    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime updateDate;

    @Column(name="sys_update_user")
    public String updateUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_prodotto")
    public TipoProdotto tipoProdotto;

    @Column(name = "unita_misura_sacco")
    public String unitMisuSacco;

    @Column(name = "quantita_sacco")
    public Double qtaSacco;

    @Column(name = "quantita_pedana")
    public Double qtaPedana;

}
