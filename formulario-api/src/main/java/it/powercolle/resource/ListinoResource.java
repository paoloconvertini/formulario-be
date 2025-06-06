package it.powercolle.resource;

import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import it.powercolle.dto.*;
import it.powercolle.entity.Listino;
import it.powercolle.entity.ValoreListino;
import it.powercolle.service.ListinoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static it.powercolle.enums.Ruolo.ADMIN;

@Path("api/listini")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListinoResource {

    @Inject
    ListinoService service;

    @GET
    @RolesAllowed({ADMIN})
    @Path("/{id}")
    public List<ListinoDto> listiniByIdProdotto(String id) {
        return service.listiniByIdProdotto(id);
    }

    @POST
    @Path("/salva-data")
    @RolesAllowed({ADMIN})
    @Transactional
    public Response salvaData(ListinoDto dto) {
        try {
            ValoreListino.update("dataValidita =?1 where 1=1", dto.getDataValidita());
            return Response.ok().entity(new ResponseDto("Data salvata correttamente", false)).build();
        } catch (Exception e){
            return Response.serverError().entity(new ResponseDto("Errore nel salvataggio della data di validità", true)).build();
        }
    }


    @GET
    @RolesAllowed({ADMIN})
    public List<ListinoDto> getAll() {
        return ValoreListino.find("select l.id, l.dataValidita FROM ValoreListino l ").project(ListinoDto.class).list();
    }

    @GET
    @RolesAllowed({ADMIN})
    @Path("/dettaglio/{id}")
    public List<TipoProdottoPdfDto> listiniByIdValoreListino(String id) {
        List<TipoProdottoPdfDto> list = service.getListiniGroupByCategoria(id);
        for (TipoProdottoPdfDto tipoProdottoPdfDto : list) {
            List<ProdottoPdfDto> prodottoPdfDtos = tipoProdottoPdfDto.getProdottoPdfDtos();
            for (ProdottoPdfDto p : prodottoPdfDtos) {
                if(StringUtils.equals("KG", p.getProdottoUnitMisuSacco())) {
                    if(p.getProdottoQtaSacco() >=20){
                        p.setRicavoQle("€/Q.LE " + String.format("%.2f", p.getRicavo()));
                        p.setRicavoQleIva("€/Q.LE " + String.format("%.2f", (p.getRicavo() + (p.getRicavo()*0.22))));
                    }
                    p.setRicavoPz("€/PZ " + String.format("%.2f", (p.getRicavo() * (p.getProdottoQtaSacco() / 100))));
                    p.setRicavoPzIva("€/PZ " + String.format("%.2f", ((p.getRicavo() + (p.getRicavo()*0.22)) * (p.getProdottoQtaSacco() / 100))));
                } else if (StringUtils.equals("QL", p.getProdottoUnitMisuSacco())){
                    p.setRicavoQle("€/Q.LE " + String.format("%.2f", p.getRicavo()));
                    p.setRicavoQleIva("€/Q.LE " + String.format("%.2f", (p.getRicavo() + (p.getRicavo()*0.22))));
                } else {
                    p.setRicavoPz("€/PZ " + String.format("%.2f", p.getRicavo()));
                    p.setRicavoPzIva("€/PZ " + String.format("%.2f", (p.getRicavo() + (p.getRicavo()*0.22))));
                }
                if(p.getProdottoUnitMisuSacco() != null){
                    String prodottoUnitMisuSacco = p.getProdottoUnitMisuSacco();
                    if(p.getProdottoQtaSacco() != null){
                        prodottoUnitMisuSacco += " " + p.getProdottoQtaSacco().intValue();
                    }
                    p.setProdottoUnitMisuSacco(prodottoUnitMisuSacco);
                }
            }
        }
        return list;
    }

    @GET
    @Path("/genera-listino/{id}/{iva}/{pubblico}")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({ADMIN})
    public Response generaListino(String id, Character iva, Character pubblico) {
        File pdf;
        try {
            pdf = service.generaListino(id, iva, pubblico);
            if(pdf != null){
                return Response.ok(pdf).header("Content-Disposition", "attachment;filename=" + pdf.getName()).build();
            } else {
                return Response.noContent().build();
            }
        } catch (Exception e) {
            Log.error("Errore generazione listino", e);
        }
        return Response.noContent().build();
    }
}
