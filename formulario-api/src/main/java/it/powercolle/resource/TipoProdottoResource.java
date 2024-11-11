package it.powercolle.resource;

import io.quarkus.panache.common.Parameters;
import it.powercolle.dto.ListinoDto;
import it.powercolle.dto.MateriaPrimaDto;
import it.powercolle.dto.ResponseDto;
import it.powercolle.entity.Listino;
import it.powercolle.entity.MateriaPrima;
import it.powercolle.entity.TipoProdotto;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

import static it.powercolle.enums.Ruolo.ADMIN;

@Path("api/tipo-prodotti")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoProdottoResource {

    @GET
    @RolesAllowed({ADMIN})
    public List<TipoProdotto> getAll() {
        return TipoProdotto.findAll().list();
    }

    @POST
    @Transactional
    @RolesAllowed({ADMIN})
    public Response create(TipoProdotto tipoProdotto) {
        Optional<TipoProdotto> optTp = TipoProdotto.find("descrizione=?1", tipoProdotto.descrizione).firstResultOptional();
        if(optTp.isPresent()){
            return Response.notModified().entity(new ResponseDto("Tipo prodotto già presente",true, Response.Status.FOUND)).build();
        }
        tipoProdotto.persist();
        return Response.ok().build();
    }
}
