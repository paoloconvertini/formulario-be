package it.powercolle.service;

import io.quarkus.logging.Log;
import it.powercolle.entity.Listino;
import jakarta.inject.Singleton;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class JasperService {


    public File createReport(List<Listino> list) {
        if (list != null && !list.isEmpty()) {
            try {


                // 1. compile template ".jrxml" file

                // 2. parameters "empty"
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("cespiteReport", compileReport("Cespite.jrxml"));
                //parameters.put("anno",registroCespitiDto.getData() == null ? LocalDate.now().getYear() : registroCespitiDto.getData().getYear());

                JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(Collections.singletonList(list));

                JasperReport jasperReport = compileReport("Listino.jrxml");
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
                String destFileName = "Listino.pdf";
                File f = new File(destFileName);
                JasperExportManager.exportReportToPdfFile(jasperPrint, f.getName());
                return f;
            } catch (Exception e) {
                Log.error("Errore nella creazione del report registro cespiti ", e);
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
