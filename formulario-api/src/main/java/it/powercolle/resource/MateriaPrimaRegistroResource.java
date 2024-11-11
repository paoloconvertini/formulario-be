package it.powercolle.resource;

import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import it.powercolle.dto.MateriaPrimaRegistroDto;
import it.powercolle.entity.MateriaPrimaRegistro;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Comparator;
import java.util.List;

import static it.powercolle.enums.Ruolo.ADMIN;

@Path("api/materie-prime-registro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MateriaPrimaRegistroResource {

    @GET
    @RolesAllowed({ADMIN})
    @Path("/{id}")
    public List<MateriaPrimaRegistroDto> listByMateriaPrimaId(Long id) {
         return MateriaPrimaRegistro.find("select r.prezzo, r.updateDate, r.updateUser, r.materiaPrima.nome " +
                         "from MateriaPrimaRegistro r " +
                         "where r.materiaPrima.id = :id " +
                         "order by r.updateDate desc", Parameters.with("id", id))
                 .project(MateriaPrimaRegistroDto.class)
                 .list();
    }

}
