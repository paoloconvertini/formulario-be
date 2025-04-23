package it.powercolle.resource.weber;

import io.quarkus.panache.common.Parameters;
import it.powercolle.dto.MateriaPrimaRegistroDto;
import it.powercolle.entity.MateriaPrimaRegistro;
import it.powercolle.entity.weber.MateriaPrimaRegistroWeber;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import static it.powercolle.enums.Ruolo.ADMIN;

@Path("api/materie-prime-registro-weber")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MateriaPrimaRegistroWeberResource {

    @GET
    @RolesAllowed({ADMIN})
    @Path("/{id}")
    public List<MateriaPrimaRegistroDto> listByMateriaPrimaId(Long id) {
         return MateriaPrimaRegistroWeber.find("select r.prezzo, r.updateDate, r.updateUser, r.materiaPrima.nome " +
                         "from MateriaPrimaRegistroWeber r " +
                         "where r.materiaPrima.id = :id " +
                         "order by r.updateDate desc", Parameters.with("id", id))
                 .project(MateriaPrimaRegistroDto.class)
                 .list();
    }

}
