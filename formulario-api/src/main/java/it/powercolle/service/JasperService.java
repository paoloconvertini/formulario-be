package it.powercolle.service;

import io.quarkus.logging.Log;
import it.powercolle.dto.ListinoPdfDto;
import it.powercolle.dto.ProdottoPdfDto;
import it.powercolle.dto.TipoProdottoPdfDto;
import jakarta.inject.Singleton;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class JasperService {


    public File createReport(List<TipoProdottoPdfDto> list, Character iva, Character pubblico) {
        if (list != null && !list.isEmpty()) {
            try {
                Map<String, Object> parameters = new HashMap<>();
                LocalDate data = LocalDate.now();
                for (TipoProdottoPdfDto tipoProdottoPdfDto : list) {
                    List<ProdottoPdfDto> prodottoPdfDtos = tipoProdottoPdfDto.getProdottoPdfDtos();
                    for (ProdottoPdfDto p : prodottoPdfDtos) {
                        if(p.getDataValidita() != null) {
                            data = p.getDataValidita();
                        }
                        if(pubblico == '1' && p.getProdottoPrezzoPubblico()!=null && p.getProdottoPrezzoPubblico()>0){
                            p.setRicavo(p.getProdottoPrezzoPubblico());
                        }
                        if('1' == iva){
                            p.setRicavo(p.getRicavo() + (p.getRicavo()*0.22));
                            tipoProdottoPdfDto.setHeaderImponibile("IMP. CON IVA");
                        } else {
                            tipoProdottoPdfDto.setHeaderImponibile("IMPONIBILE");
                        }
                        if(StringUtils.equals("KG", p.getProdottoUnitMisuSacco())){
                            p.setRicavoPz("€/Q.LE " + String.format("%.2f", p.getRicavo()));
                            p.setRicavoQle("€/PZ " + String.format("%.2f", (p.getRicavo()*(p.getProdottoQtaSacco()/100))));
                        } else {
                            p.setRicavoQle("€/PZ " + String.format("%.2f", p.getRicavo()));
                        }
                        if(p.getProdottoUnitMisuSacco() != null){
                            p.setProdottoUnitMisuSacco(p.getProdottoUnitMisuSacco() + " " + String.format("%s", p.getProdottoQtaSacco()));
                        }
                    }
                }

                parameters.put("categoriaReport", compileReport("Categoria_senza_iva.jrxml"));
                parameters.put("prodottoReport", compileReport("Prodotto_senza_iva.jrxml"));

                ListinoPdfDto l = new ListinoPdfDto();
                l.setData(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                l.setCategoriaList(list);

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
}
