package it.powercolle.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoMateriaPrimaWeberEnum {
    LAVORO("LA", "LAVORO"),
    POWER_COLLE("PC", "POWER COLLE"),
    WEBER("WE", "WEBER"),
    IMBALLO_PC("IP", "IMBALLO POWER COLLE"),
    IMBALLO_WE("IW", "IMBALLO WEBER");

    private String codice;

    private String descrizione;

}
