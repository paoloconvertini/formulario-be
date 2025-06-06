package it.powercolle.service;

import io.quarkus.logging.Log;
import it.powercolle.dto.*;
import it.powercolle.enums.TipoMateriaPrimaEnum;
import jakarta.inject.Singleton;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Singleton
public class JasperService {


    public File createReport(List<TipoProdottoPdfDto> list, Character iva, Character pubblico) {
        if (list != null && !list.isEmpty()) {
            try {
                Map<String, Object> parameters = new HashMap<>();
                LocalDate data = LocalDate.now();
                String valoreListino = "";
                for (TipoProdottoPdfDto tipoProdottoPdfDto : list) {
                    List<ProdottoPdfDto> prodottoPdfDtos = tipoProdottoPdfDto.getProdottoPdfDtos();
                    for (ProdottoPdfDto p : prodottoPdfDtos) {
                        if (p.getDataValidita() != null) {
                            data = p.getDataValidita();
                        }
                        if (pubblico == '1' && p.getProdottoPrezzoPubblico() != null && p.getProdottoPrezzoPubblico() > 0) {
                            p.setRicavo(p.getProdottoPrezzoPubblico());
                        }
                        valoreListino = String.format("%.2f", p.getValoreListinoValore());
                        if ('1' == iva) {
                            p.setRicavo(p.getRicavo() + (p.getRicavo() * 0.22));
                            tipoProdottoPdfDto.setHeaderImponibile("Prezzo imp.a Q.LE Iva compresa");
                            if (tipoProdottoPdfDto.getTipoProdottoId() == 11) {
                                tipoProdottoPdfDto.setHeaderSacco("Euro a rotolo iva compresa");
                            } else {
                                tipoProdottoPdfDto.setHeaderSacco("Prezzo a sacco iva compresa");
                            }
                        } else {
                            tipoProdottoPdfDto.setHeaderImponibile("Prezzo imp.a Q.LE Iva esclusa");
                            if (tipoProdottoPdfDto.getTipoProdottoId() == 11) {
                                tipoProdottoPdfDto.setHeaderSacco("Euro a rotolo iva esclusa");
                            } else {
                                tipoProdottoPdfDto.setHeaderSacco("Prezzo a sacco iva esclusa");
                            }

                        }
                        if (StringUtils.equals("KG", p.getProdottoUnitMisuSacco())) {
                            if (p.getProdottoQtaSacco() >= 20) {
                                p.setRicavoPz("€/Q.LE " + String.format("%.2f", p.getRicavo()));
                            }
                            p.setRicavoQle("€/PZ " + String.format("%.2f", (p.getRicavo() * (p.getProdottoQtaSacco() / 100))));
                        } else if (StringUtils.equals("QL", p.getProdottoUnitMisuSacco())) {
                            p.setRicavoPz("€/Q.LE " + String.format("%.2f", p.getRicavo()));
                        } else {
                            p.setRicavoQle("€/PZ " + String.format("%.2f", p.getRicavo()));
                        }
                        if (p.getProdottoUnitMisuSacco() != null) {
                            String prodottoUnitMisuSacco = p.getProdottoUnitMisuSacco();
                            if (p.getProdottoQtaSacco() != null) {
                                prodottoUnitMisuSacco += " " + p.getProdottoQtaSacco().intValue();
                            }
                            p.setProdottoUnitMisuSacco(prodottoUnitMisuSacco);
                        }
                    }
                }

                parameters.put("categoriaReport", compileReport("Categoria_senza_iva.jrxml"));
                parameters.put("prodottoReport", compileReport("Prodotto_senza_iva.jrxml"));

                ListinoPdfDto l = new ListinoPdfDto();
                l.setData(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                l.setCategoriaList(list);
                l.setValoreListino(valoreListino);
                l.setAnno(String.valueOf(data.getYear()));

                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(Collections.singletonList(l));
                JasperReport jasperReport = compileReport("Listino.jrxml");
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
                String destFileName = "Listino.pdf";
                File f = new File(destFileName);
                JasperExportManager.exportReportToPdfFile(jasperPrint, f.getName());
                return f;
            } catch (Exception e) {
                Log.error("Errore nella creazione del listino ", e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private JasperReport compileReport(String reportName) {
        JasperReport jasperReport = null;
        try {
            InputStream reportStream = getClass().getResourceAsStream("/reports/" + reportName);
            jasperReport = JasperCompileManager.compileReport(reportStream);
            JRSaver.saveObject(jasperReport, reportName.replace(".jrxml", ".jasper"));
        } catch (JRException ex) {
            Log.error("Error compliling report", ex);
        }
        return jasperReport;
    }

    public File createFormulaReport(List<ProdottoMateriePrimeDto> list, List<ListinoDto> listinoDtos) {
        if (list != null && !list.isEmpty()) {
            try {
                Map<String, Object> parameters = new HashMap<>();
                LocalDateTime data = LocalDateTime.now();
                List<MateriePrimePdfDto> materiePrimePdfDtos = new ArrayList<>();
                List<RicaricoDto> ricavi = new ArrayList<>();
                FormulaPdfDto f = new FormulaPdfDto();
                Double totLavoroImballo = 0D;
                for (ProdottoMateriePrimeDto dto : list) {
                    f.setNomeProdotto(dto.getProdottoNome());
                    if(TipoMateriaPrimaEnum.LAVORO.getDescrizione().equals(dto.getMateriaPrimaTipologia())) {
                        f.setLavoro(dto.getMateriaPrimaNome());
                        f.setPrezzoLavoro(String.format("%.2f", dto.getMateriaPrimaPrezzo()) + " €");
                        f.setPrezzoLavoro20(String.format("%.2f", dto.getMateriaPrimaPrezzo()*20) + " €");
                        totLavoroImballo += dto.getMateriaPrimaPrezzo();
                    } else if(TipoMateriaPrimaEnum.IMBALLO.getDescrizione().equals(dto.getMateriaPrimaTipologia())){
                        f.setImballo(dto.getMateriaPrimaNome());
                        f.setPrezzoImballo(String.format("%.2f", dto.getMateriaPrimaPrezzo()) + " €");
                        f.setPrezzoImballo20(String.format("%.2f", dto.getMateriaPrimaPrezzo()*20) + " €");
                        totLavoroImballo += dto.getMateriaPrimaPrezzo();
                    } else {
                        MateriePrimePdfDto materiePrimePdfDto = new MateriePrimePdfDto();
                        materiePrimePdfDto.setMateriaPrimaNome(dto.getMateriaPrimaNome());
                        materiePrimePdfDto.setPercentualeQL(String.format("%.2f", dto.getPercentuale() * 20));
                        materiePrimePdfDto.setPercentuale(String.format("%.2f", dto.getPercentuale()) + " %");
                        materiePrimePdfDto.setTotaleQL20(String.format("%.2f", dto.getPercentuale() * 20 / 100));
                        materiePrimePdfDto.setPrezzo(String.format("%.2f", dto.getMateriaPrimaPrezzo()) + " €");
                        materiePrimePdfDto.setCostoTotale20(String.format("%.2f",
                                (dto.getMateriaPrimaPrezzo() * dto.getPercentuale()) * 20 / 100) + " €");
                        materiePrimePdfDto.setCostoTotaleQL(String.format("%.2f",
                                (dto.getMateriaPrimaPrezzo() * dto.getPercentuale()) / 100) + " €");
                        data = dto.getProdottoUpdateDate();
                        materiePrimePdfDtos.add(materiePrimePdfDto);
                    }
                }

                if(!listinoDtos.isEmpty()){
                    for (ListinoDto listinoDto : listinoDtos) {
                        RicaricoDto ricaricoDto = new RicaricoDto();
                        ricaricoDto.setRicarico("Ricarico " + listinoDto.getValoreListinoId());
                        ricaricoDto.setValore(String.format("%.2f", listinoDto.getRicavo()) + " €");
                        ricavi.add(ricaricoDto);
                    }
                }
                parameters.put("ricaviReport", compileReport("Ricarico.jrxml"));
                parameters.put("materiePrimeReport", compileReport("Materia_Prima.jrxml"));

                List<ProdottoMateriePrimeDto> prodottoMateriePrimeDtos = list.stream().filter(m -> (!TipoMateriaPrimaEnum.IMBALLO.getDescrizione().equals(m.getMateriaPrimaTipologia())
                        && !TipoMateriaPrimaEnum.LAVORO.getDescrizione().equals(m.getMateriaPrimaTipologia()))).toList();
                double sommaPerc = prodottoMateriePrimeDtos.stream()
                        .mapToDouble(ProdottoMateriePrimeDto::getPercentuale).sum();
                f.setSommaPerc(String.format("%.2f", sommaPerc));
                f.setTotMassa(String.format("%.2f", sommaPerc*20));
                f.setTotMiscela20(String.format("%.2f", sommaPerc*20/100));

                double prezzo = prodottoMateriePrimeDtos.stream().mapToDouble(m -> (m.getMateriaPrimaPrezzo() * m.getPercentuale())).sum();
                f.setPrezzo20(String.format("%.2f",  (prezzo * 20)/ 100) + " €");
                f.setPrezzoUnitario(String.format("%.2f",  prezzo/ 100) + " €");
                f.setData(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                f.setRicaviList(ricavi);
                f.setMateriePrimeList(materiePrimePdfDtos);
                totLavoroImballo += (prezzo/ 100);
                f.setTotLavoroImballo(String.format("%.2f", totLavoroImballo) + " €");
                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(Collections.singletonList(f));
                JasperReport jasperReport = compileReport("Formula.jrxml");
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
                String destFileName = "Formula.pdf";
                File file = new File(destFileName);
                JasperExportManager.exportReportToPdfFile(jasperPrint, file.getName());
                return file;
            } catch (Exception e) {
                Log.error("Errore nella creazione del listino ", e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
