package it.powercolle.entity.weber;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProdottoMateriePrimeWeberId implements Serializable {

    private Long prodotto;

    private Long materiaPrima;
}
