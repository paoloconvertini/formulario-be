package it.calolenoci.resource;

import io.quarkus.logging.Log;
import it.calolenoci.dto.ErrorResponseDTO;
import it.calolenoci.dto.UserRequestDTO;
import it.calolenoci.entity.User;
import it.calolenoci.service.CryptoService;
import it.calolenoci.service.TokenService;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.io.File;

@ApplicationScoped
@Path("api/login")
@Slf4j
public class LoginResource {

    @Inject
    TokenService service;

    @Inject
    CryptoService cryptoService;


    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "500",
                            description = "Errore",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @APIResponse(
                            responseCode = "400",
                            description = "Errore di validazione",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @APIResponse(
                            responseCode = "401",
                            description = "Token non valido",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @APIResponse(
                            responseCode = "403",
                            description = "Utente non autorizzato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "Utente non trovato",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @APIResponse(
                            responseCode = "200",
                            description = "login",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = File.class)
                            )
                    )
            }
    )
    @Operation(
            summary = "Login",
            description = "Login utente",
            operationId = "login"
    )
    public Response login(UserRequestDTO dto) {
        User utente = User.findByUsernameAndPassword(dto.getUsername(), cryptoService.encrypt(dto.getPassword()));
        if(utente == null){
            Log.error("Utente non trovato!");
            throw new NotFoundException();
        }
        return Response.ok(service.generateToken(utente)).build();
    }
}
