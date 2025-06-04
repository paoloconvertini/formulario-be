package it.powercolle.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TipoProdottoPdfDto implements Serializable {

    private Long tipoProdottoId;

    private String tipoProdottoDescrizione;

    List<ProdottoPdfDto> prodottoPdfDtos;

    private String headerImponibile;

    private String headerSacco;
}
