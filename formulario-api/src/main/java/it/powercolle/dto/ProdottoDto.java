package it.powercolle.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@RegisterForReflection
@Data
@NoArgsConstructor
public class ProdottoDto implements Serializable {

    private Long id;

    private String nome;

    private Double costo;

    private LocalDateTime updateDate;

    private String updateUser;

    private String unitMisuSacco;

    private Double qtaSacco;

    private Double qtaPedana;


    /**
     * {@link it.powercolle.resource.ProdottoResource#list()}
     */
    public ProdottoDto(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}

