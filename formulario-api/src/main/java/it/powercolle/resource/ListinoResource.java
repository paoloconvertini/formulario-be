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
        return Listino.find("select l.prodotto.id, l.valoreListino.id, l.ricavo FROM Listino l WHERE l.prodotto.id = :id",
                Parameters.with("id", id)).project(ListinoDto.class).list();
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
        return service.getListiniGroupByCategoria(id);
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
