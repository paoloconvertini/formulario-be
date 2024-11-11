package it.powercolle.service;

import it.powercolle.entity.MateriaPrima;
import it.powercolle.entity.MateriaPrimaRegistro;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;

@ApplicationScoped
public class MateriaPrimaRegistroService {

    public void save(MateriaPrima materiaPrima, String user){
        MateriaPrimaRegistro materiaPrimaRegistro = new MateriaPrimaRegistro();
        materiaPrimaRegistro.materiaPrima = materiaPrima;
        materiaPrimaRegistro.prezzo = materiaPrima.prezzo;
        materiaPrimaRegistro.updateDate = LocalDateTime.now();
        materiaPrimaRegistro.updateUser = user;
        materiaPrimaRegistro.persist();
    }
}
