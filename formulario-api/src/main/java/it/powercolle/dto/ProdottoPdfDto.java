package it.powercolle.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProdottoPdfDto implements Serializable {

    private Long id;
    private Long prodottoId;
    private String prodottoNome;
    private Long prodottoTipoProdottoId;
    private String prodottoTipoProdottoDescrizione;
    private String prodottoUnitMisuSacco;
    private Double prodottoQtaSacco;
    private Double prodottoQtaPedana;
    private Long valoreListinoId;
    private Double valoreListinoValore;
    private Double ricavo;

    private String ricavoQle;

    private String ricavoPz;

    public ProdottoPdfDto(Long id, Long prodottoId, String prodottoNome, Long prodottoTipoProdottoId, String prodottoTipoProdottoDescrizione, String prodottoUnitMisuSacco, Double prodottoQtaSacco, Double prodottoQtaPedana, Long valoreListinoId, Double valoreListinoValore, Double ricavo) {
        this.id = id;
        this.prodottoId = prodottoId;
        this.prodottoNome = prodottoNome;
        this.prodottoTipoProdottoId = prodottoTipoProdottoId;
        this.prodottoTipoProdottoDescrizione = prodottoTipoProdottoDescrizione;
        this.prodottoUnitMisuSacco = prodottoUnitMisuSacco;
        this.prodottoQtaSacco = prodottoQtaSacco;
        this.prodottoQtaPedana = prodottoQtaPedana;
        this.valoreListinoId = valoreListinoId;
        this.valoreListinoValore = valoreListinoValore;
        this.ricavo = ricavo;
    }
}
