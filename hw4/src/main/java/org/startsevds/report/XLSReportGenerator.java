package org.startsevds.report;

import lombok.NonNull;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.startsevds.report.annotations.ColumnName;
import org.startsevds.report.interfaces.Report;
import org.startsevds.report.interfaces.ReportGenerator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class XLSReportGenerator<T> implements ReportGenerator<T> {

    private static List<Field> getAllFieldsUpTo(Class<?> clazz, Class<?> upperBound) {
        if (clazz == upperBound) {
            return Collections.emptyList();
        }

        return Stream
                .concat(
                        getAllFieldsUpTo(clazz.getSuperclass(), upperBound).stream(),
                        Arrays.stream(clazz.getDeclaredFields())
                )
                .toList();
    }

    @Override
    public Report generate(@NonNull List<T> entities, @NonNull String reportName) {
        Workbook workbook = new HSSFWorkbook();
        Sheet reportSheet = workbook.createSheet(reportName);

        generateFirstRow(entities.get(0), reportSheet);
        entities.forEach(entity -> generateBody(entity, reportSheet));

        return new XLSReport(workbook);
    }

    private void generateBody(T entity, Sheet sheet) {
        var row = sheet.createRow(sheet.getLastRowNum() + 1);
        fillRow(entity, row, this::getFieldValue);
    }

    private void generateFirstRow(T entity, Sheet sheet) {
        var columnNamesRow = sheet.createRow(sheet.getLastRowNum() + 1);
        fillRow(entity, columnNamesRow, this::getColumnName);
    }

    private void fillRow(T entity, Row row, BiFunction<Field, T, String> mapper) {
        getAllFieldsUpTo(entity.getClass(), Object.class)
                .stream()
                .sorted(Comparator.comparing(field -> getColumnName(field, entity)))
                .map(field -> mapper.apply(field, entity))
                .forEach(fieldChar ->
                        createCellWithValue(
                                row,
                                Math.max(row.getLastCellNum(), 0),
                                fieldChar
                        )
                );
    }

    private void createCellWithValue(Row row, int cellNum, String value) {
        row.createCell(cellNum).setCellValue(value);
    }

    private String getColumnName(Field field, T entity) {
        if (field.isAnnotationPresent(ColumnName.class)) {
            return field.getAnnotation(ColumnName.class).name();
        }

        return field.getName();
    }

    private String getFieldValue(Field field, T entity) {
        field.setAccessible(true);

        try {
            return String.valueOf(field.get(entity));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
