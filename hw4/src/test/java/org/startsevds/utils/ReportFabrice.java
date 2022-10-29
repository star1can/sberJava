package org.startsevds.utils;

import lombok.NonNull;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.startsevds.report.XLSReport;
import org.startsevds.report.interfaces.Report;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReportFabrice {
    public static Report XLSReportFromBytes(@NonNull byte[] bytes) {
        try (var bis = new ByteArrayInputStream(bytes);) {
            Workbook workbook = new HSSFWorkbook(bis);
            return new XLSReport(workbook);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Report readXLSReport(String path) {
        try {
            var bytes = Files.readAllBytes(Path.of(path));
            return XLSReportFromBytes(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
