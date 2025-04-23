package it.powercolle.resource.weber;

import it.powercolle.dto.MateriaPrimaDto;
import it.powercolle.dto.ResponseDto;
import it.powercolle.entity.weber.MateriaPrimaWeber;
import it.powercolle.service.MateriaPrimaRegistroService;
import it.powercolle.service.weber.MateriaPrimaRegistroWeberService;
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

@Path("api/materie-prime-weber")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class MateriaPrimaWeberResource {

    @Inject
    @Claim(standard = Claims.upn)
    String user;

    @Inject
    MateriaPrimaRegistroWeberService materiaPrimaRegistroService;

    @GET
    @RolesAllowed({ADMIN})
    public List<MateriaPrimaWeber> list() {
        return MateriaPrimaWeber.listAll();
    }


    @POST
    @Transactional
    @RolesAllowed({ADMIN})
    public Response create(MateriaPrimaDto dto) {
        MateriaPrimaWeber materiaPrima = new MateriaPrimaWeber();
        materiaPrima.nome = dto.getNome();
        Optional<MateriaPrimaWeber> optMP = MateriaPrimaWeber.find("nome=?1", dto.getNome()).firstResultOptional();
        if(optMP.isPresent()){
            return Response.notModified().entity(new ResponseDto("Materia prima già presente",true, Response.Status.FOUND)).build();
        }
        materiaPrima.unitaMisura = dto.getUnitaMisura();
        materiaPrima.prezzo = dto.getPrezzo();
        materiaPrima.tipologia = dto.getTipologia();
        materiaPrima.persist();
        materiaPrimaRegistroService.save(materiaPrima, user);
        return Response.ok().entity(new ResponseDto("Materia prima aggiunta correttamente", false)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({ADMIN})
    public MateriaPrimaWeber update(Long id, MateriaPrimaDto dto) {
        MateriaPrimaWeber entity = MateriaPrimaWeber.findById(id);
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
        if(StringUtils.isNotBlank(dto.getTipologia())) {
            entity.tipologia = dto.getTipologia();
        }
        materiaPrimaRegistroService.save(entity, user);
        return entity;
    }
}
