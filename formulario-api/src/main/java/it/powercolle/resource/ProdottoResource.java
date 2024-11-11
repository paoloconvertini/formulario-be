package it.powercolle.resource;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.powercolle.dto.ProdottoDto;
import it.powercolle.dto.ProdottoMateriePrimeDto;
import it.powercolle.entity.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static it.powercolle.enums.Ruolo.ADMIN;

@Path("api/prodotti")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProdottoResource {

    @Inject
    @Claim(standard = Claims.upn)
    String user;

    @GET
    @RolesAllowed({ADMIN})
    public List<ProdottoDto> list() {
        return Prodotto.find("select p.id, p.nome FROM Prodotto p").project(ProdottoDto.class).list();
    }

}
