package it.powercolle.resource;

import io.quarkus.logging.Log;
import it.powercolle.dto.ProdottoMateriePrimeDto;
import it.powercolle.dto.ResponseDto;
import it.powercolle.entity.*;
import it.powercolle.enums.TipoMateriaPrimaEnum;
import it.powercolle.service.ProdottoService;
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

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static it.powercolle.enums.Ruolo.ADMIN;

@Path("api/prodotto-materie-prime")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProdottoMateriePrimeResource {

    @Inject
    @Claim(standard = Claims.upn)
    String user;

    @Inject
    ProdottoService service;

    @GET
    @RolesAllowed({ADMIN})
    @Path("/{id}")
    public List<ProdottoMateriePrimeDto> ricettaById(Long id) {
        return service.ricettaById(id);
    }

    @POST

    @RolesAllowed({ADMIN})
    public Response create(List<ProdottoMateriePrimeDto> prodottoMateriePrimeDtoList) {
        if(service.create(prodottoMateriePrimeDtoList, user)){
            return Response.ok().entity(new ResponseDto("Prodotto salvato con successo!", false)).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/stampa-formula/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({ADMIN})
    public Response generaListino(Long id) {
        File pdf;
        try {
            pdf = service.generaListino(id);
            if(pdf != null){
                return Response.ok(pdf).header("Content-Disposition", "attachment;filename=" + pdf.getName()).build();
            } else {
                return Response.noContent().build();
            }
        } catch (Exception e) {
            Log.error("Errore generazione listino", e);
        }
        return Response.noContent().build();
    }
}
