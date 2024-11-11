package it.powercolle.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link it.powercolle.entity.MateriaPrima} entity
 */
@Data
@RegisterForReflection
public class MateriaPrimaDto implements Serializable {
    private final String nome;
    private final String unitaMisura;
    private final Double prezzo;

}