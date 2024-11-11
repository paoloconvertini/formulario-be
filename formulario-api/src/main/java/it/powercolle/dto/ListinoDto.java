package it.powercolle.dto;

import lombok.*;

import java.io.Serializable;

/**
 * A DTO for the {@link it.powercolle.entity.Listino} entity
 */
@Getter
@Setter
@NoArgsConstructor
public class ListinoDto implements Serializable {

    private Long prodottoId;
    private Long valoreListinoId;
    private Double ricavo;


    /**
     * {@link it.powercolle.resource.ListinoResource#listiniByIdProdotto(String)}
     * @param prodottoId id prodotto
     * @param valoreListinoId
     * @param ricavo
     */
    public ListinoDto(Long prodottoId, Long valoreListinoId, Double ricavo) {
        this.prodottoId = prodottoId;
        this.valoreListinoId = valoreListinoId;
        this.ricavo = ricavo;
    }

    /**
     * {@link it.powercolle.resource.ListinoResource#getAll()}}
     * @param valoreListinoId
     */
    public ListinoDto(Long valoreListinoId) {
        this.valoreListinoId = valoreListinoId;
    }
}