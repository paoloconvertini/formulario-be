package it.powercolle.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link it.powercolle.entity.Listino} entity
 */
@Data
public class FormulaPdfDto implements Serializable {

    private String data;
	private String nomeProdotto;
	private String imballo;
	private String lavoro;
	private String prezzoImballo;
	private String prezzoImballo20;
	private String prezzoLavoro;
	private String prezzoLavoro20;
	private List<RicaricoDto> ricaviList;
	private List<MateriePrimePdfDto> materiePrimeList;
	
	private String totMassa;
	private String sommaPerc;
	private String totMiscela20;
	private String prezzo20;
	private String prezzoUnitario;
	private String totLavoroImballo;
}