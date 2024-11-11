package it.powercolle.resource;

import io.quarkus.logging.Log;
import it.powercolle.service.ListinoService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;

@Path("api/pdf/listini")
public class ListinoPdfResource {

    @Inject
    ListinoService service;

    @GET
    @Path("/generaListino/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
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
