package it.powercolle.resource.weber;

import io.quarkus.logging.Log;
import it.powercolle.dto.ResponseDto;
import it.powercolle.entity.weber.TipoProdottoWeber;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

import static it.powercolle.enums.Ruolo.ADMIN;

@Path("api/tipo-prodotti-weber")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoProdottoWeberResource {

    @GET
    @RolesAllowed({ADMIN})
    public List<TipoProdottoWeber> getAll() {
        return TipoProdottoWeber.findAll().list();
    }

    @POST
    @Transactional
    @RolesAllowed({ADMIN})
    public Response create(TipoProdottoWeber tipoProdotto) {
        try {
            if (tipoProdotto.id == null) {
                tipoProdotto.persist();
                return Response.ok(new ResponseDto("Tipo prodotto creato con successo", false)).build();
            }

            Optional<TipoProdottoWeber> optTp = TipoProdottoWeber.find("id=?1", tipoProdotto.id).firstResultOptional();
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
