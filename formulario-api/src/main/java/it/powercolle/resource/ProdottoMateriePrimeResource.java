package it.powercolle.resource;

import it.powercolle.dto.ProdottoMateriePrimeDto;
import it.powercolle.dto.ResponseDto;
import it.powercolle.entity.*;
import it.powercolle.enums.TipoMateriaPrimaEnum;
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

    @GET
    @RolesAllowed({ADMIN})
    @Path("/{id}")
    public List<ProdottoMateriePrimeDto> ricettaById(Long id) {
        return Prodotto.find("SELECT p.prodotto.id, p.prodotto.nome, p.prodotto.costo, p.prodotto.unitMisuSacco, " +
                        "p.prodotto.qtaSacco, p.prodotto.qtaPedana, p.prodotto.updateDate, " +
                        "p.prodotto.updateUser, p.prodotto.prezzoPubblico, p.materiaPrima.id, p.materiaPrima.nome, p.materiaPrima.unitaMisura, " +
                        "p.materiaPrima.prezzo, p.percentuale, p.materiaPrima.tipologia, p.prodotto.tipoProdotto " +
                        "FROM ProdottoMateriePrime p " +
                        "WHERE p.prodotto.id =?1", id)
                .project(ProdottoMateriePrimeDto.class).list();
    }

    @POST
    @Transactional
    @RolesAllowed({ADMIN})
    public Response create(List<ProdottoMateriePrimeDto> prodottoMateriePrimeDtoList) {
        ProdottoMateriePrimeDto lavoro = prodottoMateriePrimeDtoList.stream()
                .filter(dto -> TipoMateriaPrimaEnum.LAVORO.getDescrizione().equals(dto.getMateriaPrimaTipologia())).findFirst().orElse(null);
        ProdottoMateriePrimeDto imballo = prodottoMateriePrimeDtoList.stream()
                .filter(dto -> TipoMateriaPrimaEnum.IMBALLO.getDescrizione().equals(dto.getMateriaPrimaTipologia())).findFirst().orElse(null);
        if(prodottoMateriePrimeDtoList.isEmpty()){
            return Response.noContent().build();
        }
        List<ProdottoMateriePrime> listToSave = new ArrayList<>();
        AtomicReference<Double> costo = new AtomicReference<>(0D);
        AtomicReference<Double> percentuale = new AtomicReference<>(0D);
        Prodotto prodotto = new Prodotto();
        //se non esiste id sono in creazione
        if(prodottoMateriePrimeDtoList.get(0).getProdottoId() == null){
            prodotto.nome = prodottoMateriePrimeDtoList.get(0).getProdottoNome();
            prodotto.updateDate = LocalDateTime.now();
            prodotto.updateUser = user;
            prodotto.persist();
            prodotto = Prodotto.find("nome =?1", prodotto.nome).firstResult();
        } else {
            // sono in modifica, controllo cmq che mi arrivi un id corretto
            prodotto = Prodotto.findById(prodottoMateriePrimeDtoList.get(0).getProdottoId());
        }
        if (prodotto == null) {
            throw new RuntimeException("Nessun prodotto trovato con nome " + prodottoMateriePrimeDtoList.get(0).getProdottoNome());
        }
        for (ProdottoMateriePrimeDto dto : prodottoMateriePrimeDtoList) {
            ProdottoMateriePrime entity = ProdottoMateriePrime.findById(new ProdottoMateriePrimeId(dto.getProdottoId(), dto.getMateriaPrimaId()));
            MateriaPrima materiaPrima = MateriaPrima.findById(dto.getMateriaPrimaId());
            if(materiaPrima == null) {
                throw  new RuntimeException(("Nessuna materia prima trovata con nome " + dto.getMateriaPrimaNome()));
            }
            if(entity == null) {
                entity = new ProdottoMateriePrime();
                entity.materiaPrima = materiaPrima;
                entity.prodotto = prodotto;
            }
            entity.percentuale = dto.getPercentuale();
            ProdottoMateriePrime finalEntity = entity;
            if(StringUtils.isBlank(dto.getMateriaPrimaTipologia())) {
                percentuale.updateAndGet(p -> p + finalEntity.percentuale);
                if(percentuale.get() > 100) {
                    throw  new RuntimeException("ATTENZIONE! La percentuale degli additivi non può essere maggiore del 100%");
                }
                costo.updateAndGet(v -> v + finalEntity.percentuale / 100 * materiaPrima.prezzo);
            }
            listToSave.add(entity);
        }
        prodotto.tipoProdotto = TipoProdotto.findById(prodottoMateriePrimeDtoList.get(0).getTipoProdotto().id);
        if(StringUtils.isNotEmpty(prodottoMateriePrimeDtoList.get(0).getProdottoUnitMisuSacco())) {
            prodotto.unitMisuSacco = prodottoMateriePrimeDtoList.get(0).getProdottoUnitMisuSacco();
        }
        if(prodottoMateriePrimeDtoList.get(0).getProdottoQtaSacco() != null){
            prodotto.qtaSacco = prodottoMateriePrimeDtoList.get(0).getProdottoQtaSacco();
        }
        if(prodottoMateriePrimeDtoList.get(0).getProdottoQtaPedana() != null) {
            prodotto.qtaPedana = prodottoMateriePrimeDtoList.get(0).getProdottoQtaPedana();
        }
        if(prodottoMateriePrimeDtoList.get(0).getProdottoNome() != null) {
            prodotto.nome = prodottoMateriePrimeDtoList.get(0).getProdottoNome();
        }
        if(prodottoMateriePrimeDtoList.get(0).getProdottoPrezzoPubblico() != null) {
            prodotto.prezzoPubblico = prodottoMateriePrimeDtoList.get(0).getProdottoPrezzoPubblico();
        }
        prodotto.updateDate = LocalDateTime.now();
        prodotto.costo = costo.get();
        prodotto.persist();
        ProdottoMateriePrime.persist(listToSave);
        if(lavoro != null && imballo !=null){
            salvaListini(prodotto, lavoro, imballo);
        }
        return Response.ok().entity(new ResponseDto("Prodotto salvato con successo!", false)).build();
    }

    private void salvaListini(Prodotto prodotto, ProdottoMateriePrimeDto lavoro, ProdottoMateriePrimeDto imballo) {
        List<Listino> listToSave = new ArrayList<>();
        List<ValoreListino> valoreListinoList = ValoreListino.findAll().list();
        valoreListinoList.forEach(v -> {
            Optional<Listino> optListino = Listino.find("select l FROM Listino l " +
                    "WHERE l.prodotto.id =?1 AND l.valoreListino.id =?2", prodotto.id, v.id).firstResultOptional();
            Listino listino;
            if(optListino.isEmpty()) {
                listino = new Listino();
                listino.prodotto = prodotto;
                listino.valoreListino = v;
            } else {
                listino = optListino.get();
            }
            listino.ricavo = (prodotto.costo + lavoro.getMateriaPrimaPrezzo() + imballo.getMateriaPrimaPrezzo())*v.valore;
            listToSave.add(listino);
        });
        Listino.persist(listToSave);
    }
}
