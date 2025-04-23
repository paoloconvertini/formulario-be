package it.powercolle.service.weber;

import it.powercolle.entity.weber.MateriaPrimaRegistroWeber;
import it.powercolle.entity.weber.MateriaPrimaWeber;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;

@ApplicationScoped
public class MateriaPrimaRegistroWeberService {

    public void save(MateriaPrimaWeber materiaPrima, String user){
        MateriaPrimaRegistroWeber materiaPrimaRegistro = new MateriaPrimaRegistroWeber();
        materiaPrimaRegistro.materiaPrima = materiaPrima;
        materiaPrimaRegistro.prezzo = materiaPrima.prezzo;
        materiaPrimaRegistro.updateDate = LocalDateTime.now();
        materiaPrimaRegistro.updateUser = user;
        materiaPrimaRegistro.persist();
    }
}
