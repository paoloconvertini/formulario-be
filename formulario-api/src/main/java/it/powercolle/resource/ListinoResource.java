package it.powercolle.resource;

import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import it.powercolle.dto.ListinoDto;
import it.powercolle.dto.ListinoPdfDto;
import it.powercolle.dto.ProdottoMateriePrimeDto;
import it.powercolle.dto.TipoProdottoPdfDto;
import it.powercolle.entity.Listino;
import it.powercolle.service.ListinoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
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
        return Listino.find("select l.prodotto.id, l.valoreListino.id, l.ricavo FROM Listino l WHERE l.prodotto.id = :id",
                Parameters.with("id", id)).project(ListinoDto.class).list();
    }


    @GET
    @RolesAllowed({ADMIN})
    public List<ListinoDto> getAll() {
        return Listino.find("select distinct l.valoreListino.id FROM Listino l ").project(ListinoDto.class).list();
    }

    @GET
    @RolesAllowed({ADMIN})
    @Path("/dettaglio/{id}")
    public List<TipoProdottoPdfDto> listiniByIdValoreListino(String id) {
        return service.getListiniGroupByCategoria(id);
    }

    @GET
    @Path("/genera-listino/{id}/{iva}")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({ADMIN})
    public Response generaListino(String id, Character iva) {
        File pdf;
        try {
            pdf = service.generaListino(id, iva);
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
