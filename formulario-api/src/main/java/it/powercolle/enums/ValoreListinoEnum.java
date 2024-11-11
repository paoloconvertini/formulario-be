package it.powercolle.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ValoreListinoEnum {
    VALORE_LISTINO_ENUM_20(20L, 1.2D),
    VALORE_LISTINO_ENUM_30(30L, 1.3D),
    VALORE_LISTINO_ENUM_40(40L, 1.4D),
    VALORE_LISTINO_ENUM_50(50L, 1.5D),
    VALORE_LISTINO_ENUM_60(60L, 1.6D),
    VALORE_LISTINO_ENUM_70(70L, 1.7D),
    VALORE_LISTINO_ENUM_80(80L, 1.8D);

    private final Long id;

    private final Double descrizione;

}
