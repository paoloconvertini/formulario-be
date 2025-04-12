package it.powercolle.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TipoProdottoPdfDto implements Serializable {



    private String tipoProdottoDescrizione;

    List<ProdottoPdfDto> prodottoPdfDtos;
}
