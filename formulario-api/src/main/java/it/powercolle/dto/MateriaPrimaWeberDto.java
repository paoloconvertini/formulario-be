package it.powercolle.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link it.powercolle.entity.MateriaPrima} entity
 */
@Data
@RegisterForReflection
public class MateriaPrimaWeberDto implements Serializable {
    private String nome;
    private String unitaMisura;
    private Double prezzo;
    private MateriaPrimaTipoDto tipologia;

}