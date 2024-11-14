package it.powercolle.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProdottoPdfDto implements Serializable {

    private final Long id;
    private final Long prodottoId;
    private final String prodottoNome;
    private final Long prodottoTipoProdottoId;
    private final String prodottoTipoProdottoDescrizione;
    private final String prodottoUnitMisuSacco;
    private final Double prodottoQtaSacco;
    private final Double prodottoQtaPedana;
    private final Long valoreListinoId;
    private final Double valoreListinoValore;
    private final Double ricavo;
}
