package it.powercolle.resource;

import io.quarkus.panache.common.Parameters;
import it.powercolle.dto.ListinoDto;
import it.powercolle.dto.ProdottoMateriePrimeDto;
import it.powercolle.entity.Listino;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import static it.powercolle.enums.Ruolo.ADMIN;

@Path("api/listini")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListinoResource {

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
    public List<Listino> listiniByIdValoreListino(String id) {
        return Listino.find("valoreListino.id = :id",
                Parameters.with("id", id)).list();
    }
}
