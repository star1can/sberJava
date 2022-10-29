package org.startsevds.utils.compare;

import lombok.NonNull;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.startsevds.report.interfaces.Report;

public class ReportCompare {
    private static String MISSING_NAME_ERROR = "No field with name: \"workbook\" found";

    private static Workbook getInnerWorkbook(Report r) {
        try {
            var field = r.getClass().getDeclaredField("workbook");
            field.setAccessible(true);
            return (Workbook) field.get(r);
        } catch (Exception e) {
            throw new RuntimeException(MISSING_NAME_ERROR);
        }
    }

    public static boolean isXLSReportsTheSame(@NonNull Report lhs, @NonNull Report rhs) {
        Workbook w1 = getInnerWorkbook(lhs);
        Workbook w2 = getInnerWorkbook(rhs);
        return isWorkbooksTheSame(w1, w2);
    }

    private static boolean isWorkbooksTheSame(Workbook lhs, Workbook rhs) {
        if (lhs.getNumberOfSheets() != rhs.getNumberOfSheets()) {
            return false;
        }

        for (int i = 0; i < lhs.getNumberOfSheets(); ++i) {
            if (!isSheetsTheSame(lhs.getSheetAt(i), rhs.getSheetAt(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean isSheetsTheSame(Sheet lhs, Sheet rhs) {
        if (!lhs.getSheetName().equals(rhs.getSheetName())
                || lhs.getFirstRowNum() != rhs.getFirstRowNum()
                || lhs.getLastRowNum() != rhs.getLastRowNum()
        ) {
            return false;
        }

        for (int i = lhs.getFirstRowNum(); i <= lhs.getLastRowNum(); ++i) {
            if (!isRowsTheSame(lhs.getRow(i), rhs.getRow(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean isRowsTheSame(Row lhs, Row rhs) {
        if (lhs == null || rhs == null) {
            return false;
        }

        if (lhs.getFirstCellNum() != rhs.getFirstCellNum()
                || lhs.getLastCellNum() != rhs.getLastCellNum()) {
            return false;
        }

        for (int i = lhs.getFirstCellNum(); i < lhs.getLastCellNum(); ++i) {
            if (!isCellsTheSame(lhs.getCell(i), rhs.getCell(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean isCellsTheSame(Cell lhs, Cell rhs) {
        if (lhs == null || rhs == null) {
            return false;
        }

        if (lhs.getCellType() != rhs.getCellType()) {
            return false;
        }

        return extractValue(lhs).equals(extractValue(rhs));
    }

    // for now supported String types only
    private static Object extractValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            default -> throw new RuntimeException("Unsupported cell Type!");
        };
    }
}
