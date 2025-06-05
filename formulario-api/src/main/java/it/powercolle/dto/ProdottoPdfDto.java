package it.powercolle.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ProdottoPdfDto implements Serializable {

    private Long id;
    private Long prodottoId;
    private String prodottoNome;

    private Double prodottoPrezzoPubblico;
    private Long prodottoTipoProdottoId;
    private String prodottoTipoProdottoDescrizione;
    private String prodottoUnitMisuSacco;
    private Double prodottoQtaSacco;
    private Double prodottoQtaPedana;
    private Long valoreListinoId;
    private Double valoreListinoValore;

    private LocalDate dataValidita;
    private Double ricavo;

    private String ricavoQle;

    private String ricavoQleIva;

    private String ricavoPz;

    private String ricavoPzIva;

    public ProdottoPdfDto(Long id, Long prodottoId, String prodottoNome, Long prodottoTipoProdottoId, String prodottoTipoProdottoDescrizione,
                          String prodottoUnitMisuSacco, Double prodottoQtaSacco, Double prodottoQtaPedana, Double prodottoPrezzoPubblico,
                          Long valoreListinoId, Double valoreListinoValore, LocalDate dataValidita, Double ricavo) {
        this.id = id;
        this.prodottoId = prodottoId;
        this.prodottoNome = prodottoNome;
        this.prodottoTipoProdottoId = prodottoTipoProdottoId;
        this.prodottoTipoProdottoDescrizione = prodottoTipoProdottoDescrizione;
        this.prodottoUnitMisuSacco = prodottoUnitMisuSacco;
        this.prodottoQtaSacco = prodottoQtaSacco;
        this.prodottoQtaPedana = prodottoQtaPedana;
        this.prodottoPrezzoPubblico = prodottoPrezzoPubblico;
        this.valoreListinoId = valoreListinoId;
        this.valoreListinoValore = valoreListinoValore;
        this.dataValidita = dataValidita;
        this.ricavo = ricavo;
    }
}
