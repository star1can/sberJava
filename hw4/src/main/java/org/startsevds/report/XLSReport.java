package org.startsevds.report;

import org.apache.poi.ss.usermodel.Workbook;
import org.startsevds.report.interfaces.Report;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class XLSReport implements Report {
    private final Workbook workbook;

    public XLSReport(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public byte[] asBytes() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            this.writeTo(bos);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void writeTo(OutputStream os) {
        try {
            workbook.write(os);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
