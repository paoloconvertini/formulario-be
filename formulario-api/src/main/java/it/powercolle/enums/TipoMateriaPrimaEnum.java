package it.powercolle.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoMateriaPrimaEnum {
    LAVORO(1L, "LA"),
    IMBALLO(2L, "IM");

    private final Long id;

    private final String descrizione;

}
