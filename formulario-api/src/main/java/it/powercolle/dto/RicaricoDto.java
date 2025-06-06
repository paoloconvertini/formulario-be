package it.powercolle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link it.powercolle.entity.Listino} entity
 */
@Getter
@Setter
@NoArgsConstructor
public class RicaricoDto implements Serializable {

    private String ricarico;
    private String valore;



}