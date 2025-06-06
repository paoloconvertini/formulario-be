package it.powercolle.dto;

import it.powercolle.entity.ProdottoMateriePrime;
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
public class MateriePrimePdfDto implements Serializable {

    private String materiaPrimaNome;
	private String percentuale;
	private String percentualeQL;
	private String totaleQL20;
	private String costoTotale20;
	private String costoTotaleQL;
	private String prezzo;
}