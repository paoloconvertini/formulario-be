package it.powercolle.service;

import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import it.powercolle.dto.ListinoPdfDto;
import it.powercolle.dto.ProdottoPdfDto;
import it.powercolle.dto.TipoProdottoPdfDto;
import it.powercolle.entity.Listino;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ListinoService {

    @Inject
    JasperService jasperService;

    public File generaListino(String id, Character iva) {
        Log.debug(" ### INIZIO generazione report Listino " + id + " ###");
        List<TipoProdottoPdfDto> list = getListiniGroupByCategoria(id);
        File report;
            try {
                report = jasperService.createReport(list, iva);
                Log.debug(" ### FINE generazione report Listino ###");
            } catch (Exception e) {
                report = null;
                Log.error("error generazione report Listino");
            }
        return report;
    }

    public List<TipoProdottoPdfDto> getListiniGroupByCategoria(String id){
        List<ProdottoPdfDto> list = getListiniByValoreListino(id);
        Map<String, List<ProdottoPdfDto>> prodottiByTipoMap = list.stream().collect(Collectors.groupingBy(ProdottoPdfDto::getProdottoTipoProdottoDescrizione));
        List<TipoProdottoPdfDto> tipoProdottoPdfDtoList = new ArrayList<>();
        for (String desc : prodottiByTipoMap.keySet()) {
            TipoProdottoPdfDto dto = new TipoProdottoPdfDto();
            dto.setTipoProdottoId(prodottiByTipoMap.get(desc).get(0).getProdottoTipoProdottoId());
            dto.setTipoProdottoDescrizione(desc);
            dto.setProdottoPdfDtos(prodottiByTipoMap.get(desc));
            tipoProdottoPdfDtoList.add(dto);
        }
        return tipoProdottoPdfDtoList.stream().sorted(Comparator.comparing(TipoProdottoPdfDto::getTipoProdottoId)).collect(Collectors.toList());
    }

    private List<ProdottoPdfDto> getListiniByValoreListino(String id) {
        return Listino.find("select l.id, l.prodotto.id, l.prodotto.nome, " +
                        "l.prodotto.tipoProdotto.id, l.prodotto.tipoProdotto.descrizione, " +
                        "l.prodotto.unitMisuSacco, l.prodotto.qtaSacco, l.prodotto.qtaPedana, l.valoreListino.id, " +
                        "l.valoreListino.valore, l.ricavo " +
                        "FROM Listino l " +
                        "WHERE l.valoreListino.id = ?1 ", id)
                .project(ProdottoPdfDto.class).list();
    }
}
