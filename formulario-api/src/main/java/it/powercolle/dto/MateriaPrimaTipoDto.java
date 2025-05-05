package it.powercolle.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

import java.io.Serializable;

@Data
@RegisterForReflection
public class MateriaPrimaTipoDto implements Serializable {

    private String id;
    private String descrizione;
}
