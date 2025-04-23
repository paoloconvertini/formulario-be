package it.powercolle.entity.weber;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.powercolle.entity.TipoProdotto;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PRODOTTO_WEBER", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
public class ProdottoWeber extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

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
    public TipoProdottoWeber tipoProdotto;

    @Column(name = "unita_misura_sacco")
    public String unitMisuSacco;

    @Column(name = "quantita_sacco")
    public Double qtaSacco;

    @Column(name = "quantita_pedana")
    public Double qtaPedana;

    @Column(name = "prezzo_pubblico")
    public Double prezzoPubblico;

}
