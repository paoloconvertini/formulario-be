package it.powercolle.resource.weber;

import io.quarkus.panache.common.Parameters;
import it.powercolle.dto.ProdottoDto;
import it.powercolle.entity.weber.ProdottoWeber;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import java.util.List;

import static it.powercolle.enums.Ruolo.ADMIN;

@Path("api/prodotti-weber")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProdottoWeberResource {

    @Inject
    @Claim(standard = Claims.upn)
    String user;

    @GET
    @RolesAllowed({ADMIN})
    public List<ProdottoDto> list() {
        return ProdottoWeber.find("select p.id, p.nome FROM ProdottoWeber p").project(ProdottoDto.class).list();
    }
    @GET
    @RolesAllowed({ADMIN})
    @Path("/by-tipo/{id}")
    public List<ProdottoDto> listByTipoProdotto(Long id) {
        return ProdottoWeber.find("select p.id, p.nome FROM ProdottoWeber p " +
                                "where p.tipoProdotto.id=:id",
                        Parameters.with("id", id))
                .project(ProdottoDto.class).list();
    }

}
