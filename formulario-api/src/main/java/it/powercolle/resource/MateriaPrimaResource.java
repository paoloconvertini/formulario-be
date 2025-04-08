package it.powercolle.resource;

import it.powercolle.dto.MateriaPrimaDto;
import it.powercolle.dto.ResponseDto;
import it.powercolle.entity.MateriaPrima;
import it.powercolle.service.MateriaPrimaRegistroService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import java.util.List;
import java.util.Optional;

import static it.powercolle.enums.Ruolo.ADMIN;

@Path("api/materie-prime")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class MateriaPrimaResource {

    @Inject
    @Claim(standard = Claims.upn)
    String user;

    @Inject
    MateriaPrimaRegistroService materiaPrimaRegistroService;

    @GET
    @RolesAllowed({ADMIN})
    public List<MateriaPrima> list() {
        return MateriaPrima.listAll();
    }


    @POST
    @Transactional
    @RolesAllowed({ADMIN})
    public Response create(MateriaPrimaDto dto) {
        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.nome = dto.getNome();
        Optional<MateriaPrima> optMP = MateriaPrima.find("nome=?1", dto.getNome()).firstResultOptional();
        if(optMP.isPresent()){
            return Response.notModified().entity(new ResponseDto("Materia prima già presente",true, Response.Status.FOUND)).build();
        }
        materiaPrima.unitaMisura = dto.getUnitaMisura();
        materiaPrima.prezzo = dto.getPrezzo();
        materiaPrima.persist();
        materiaPrimaRegistroService.save(materiaPrima, user);
        return Response.ok().entity(new ResponseDto("Materia prima aggiunta correttamente", false)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({ADMIN})
    public MateriaPrima update(Long id, MateriaPrimaDto dto) {
        MateriaPrima entity = MateriaPrima.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        if(StringUtils.isNotBlank(dto.getNome())){
            entity.nome = dto.getNome();
        }
        if(dto.getPrezzo() != null) {
            entity.prezzo = dto.getPrezzo();
        }
        if(StringUtils.isNotBlank(dto.getUnitaMisura())) {
            entity.unitaMisura = dto.getUnitaMisura();
        }
        materiaPrimaRegistroService.save(entity, user);
        return entity;
    }
}
