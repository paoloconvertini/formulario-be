package it.powercolle.dto;

import it.powercolle.entity.ProdottoMateriePrime;
import it.powercolle.entity.TipoProdotto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ProdottoMateriePrime} entity
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class ProdottoMateriePrimeDto implements Serializable {
    private Long prodottoId;
    private  String prodottoNome;
    private  Double prodottoCosto;

    private  Double prodottoPrezzoPubblico;

    private String prodottoUnitMisuSacco;

    private Double prodottoQtaSacco;

    private Double prodottoQtaPedana;
    private  LocalDateTime prodottoUpdateDate;
    private  String prodottoUpdateUser;
    private  Long materiaPrimaId;
    private  String materiaPrimaNome;
    private  String materiaPrimaUnitaMisura;
    private  Double materiaPrimaPrezzo;

    private  Double percentuale;

    private  Long idListino;

    private  Double valoreListino;
    private String materiaPrimaTipologia;

    private TipoProdotto tipoProdotto;

    /**
     * {@link it.powercolle.resource.ProdottoMateriePrimeResource#ricettaById(Long)}  }
     */
    public ProdottoMateriePrimeDto(Long prodottoId, String prodottoNome, Double prodottoCosto, String prodottoUnitMisuSacco,
                                   Double prodottoQtaSacco, Double prodottoQtaPedana, LocalDateTime prodottoUpdateDate, String prodottoUpdateUser,
                                   Double prodottoPrezzoPubblico,
                                   Long materiaPrimaId, String materiaPrimaNome, String materiaPrimaUnitaMisura,
                                   Double materiaPrimaPrezzo, Double percentuale, String materiaPrimaTipologia,
                                   TipoProdotto tipoProdotto ) {
        this.prodottoId = prodottoId;
        this.prodottoNome = prodottoNome;
        this.prodottoCosto = prodottoCosto;
        this.prodottoUnitMisuSacco = prodottoUnitMisuSacco;
        this.prodottoQtaSacco = prodottoQtaSacco;
        this.prodottoQtaPedana = prodottoQtaPedana;
        this.prodottoUpdateDate = prodottoUpdateDate;
        this.prodottoUpdateUser = prodottoUpdateUser;
        this.prodottoPrezzoPubblico = prodottoPrezzoPubblico;
        this.materiaPrimaId = materiaPrimaId;
        this.materiaPrimaNome = materiaPrimaNome;
        this.materiaPrimaUnitaMisura = materiaPrimaUnitaMisura;
        this.materiaPrimaPrezzo = materiaPrimaPrezzo;
        this.percentuale = percentuale;
        this.materiaPrimaTipologia = materiaPrimaTipologia;
        this.tipoProdotto = tipoProdotto;
    }
}