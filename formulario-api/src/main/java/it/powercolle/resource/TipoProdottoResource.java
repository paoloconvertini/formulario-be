package it.powercolle.resource;

import io.quarkus.logging.Log;
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
        try {
            if (tipoProdotto.id == null) {
                tipoProdotto.persist();
                return Response.ok(new ResponseDto("Tipo prodotto creato con successo", false)).build();
            }

            Optional<TipoProdotto> optTp = TipoProdotto.find("id=?1", tipoProdotto.id).firstResultOptional();
            if (optTp.isPresent()) {
                optTp.get().descrizione = tipoProdotto.descrizione;
                optTp.get().persist();
                return Response.ok().entity(optTp.get()).build();
            }
        } catch (Exception e) {
            Log.error("Errore salvataggio Tipo prodotto", e);
        }
        return Response.notModified().build();
    }
}
