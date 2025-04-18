package it.calolenoci.resource;

import io.quarkus.panache.common.Sort;
import it.calolenoci.dto.ResponseDTO;
import it.calolenoci.dto.RoleDto;
import it.calolenoci.entity.Role;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import static it.calolenoci.constant.Ruolo.ADMIN;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("api/roles")
@RolesAllowed(ADMIN)
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class RoleResource {


    @POST
    @Transactional
    @APIResponse(responseCode = "200", description = "Role salvato con successo")
    public Response saveRole(RoleDto ruolo) {
        Role entity = new Role();
        entity.name = ruolo.getName();
        entity.persist();
        return Response.status(Response.Status.CREATED).entity(new ResponseDTO("Ruolo salvato", false)).build();
    }

    @GET
    @Path("/{idRole}")
    public Response getRole(Long idRole){
        Role role = findById(idRole);
        return Response.ok(role).build();
    }

    @Operation(summary = "Returns all the roles from the database")
    @GET
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Role.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No Roles")
    public Response getAllRoles() {
        return Response.ok(Role.findAll(Sort.ascending("name")).list()).build();
    }

    @DELETE
    @Transactional
    @Path("/{idRole}")
    public Response delete(Long idRole) {
        this.findById(idRole).delete();
        return Response.ok().entity(new ResponseDTO("Ruolo eliminato", false)).build();
    }

    @PUT
    @Path("/{id}")
    @APIResponse(responseCode = "404", description = "Role non trovato")
    @APIResponse(responseCode = "200", description = "Role aggiornato con successo")
    @Transactional
    public Response update(Long id, RoleDto ruolo){
        Role role = findById(id);
        role.name = ruolo.getName();
        return Response.status(Response.Status.CREATED).entity(new ResponseDTO("Ruolo aggiornato", false)).build();
    }

    private Role findById(Long id) {
        Role entity = Role.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }
        return entity;
    }
}