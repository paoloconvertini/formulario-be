package it.powercolle.service;

import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import it.powercolle.dto.ListinoDto;
import it.powercolle.entity.Listino;
import it.powercolle.entity.MateriaPrima;
import it.powercolle.entity.MateriaPrimaRegistro;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ListinoService {

    @Inject
    JasperService jasperService;

    public List<Listino> findListinoByProdottoId(Long id) {
        return Listino.find("select l FROM Listino l WHERE l.prodotto.id = :id",
                Parameters.with("id", id)).list();
    }

    public File generaListino(Long id) {
        Log.debug(" ### INIZIO generazione report Listino " + id + " ###");
        List<Listino> list = Listino.find("valoreListino.id = ?1", id).list();
            File report;
            try {
                report = jasperService.createReport(list);
                Log.debug(" ### FINE generazione report Registro cespiti ###");
            } catch (Exception e) {
                report = null;
                Log.error("error scarica regisro");
            }
        return report;
    }
}
