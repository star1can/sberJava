package org.startsevds.report;

import org.junit.jupiter.api.*;
import org.startsevds.report.interfaces.ReportGenerator;
import org.startsevds.utils.compare.ReportCompare;
import org.startsevds.utils.simple_test.Derived;
import org.startsevds.utils.annotation_test.DerivedAnnotated;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.startsevds.utils.ReportFabrice.*;


public class ReportTest {
    private String workPath = "simpleTest.xls";

    private String getTestPath(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        return Objects.requireNonNull(classLoader.getResource(filename)).getPath();
    }

    @AfterEach
    public void clearWorkspace() {
        try {
            Files.deleteIfExists(Path.of(workPath));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void simpleTest() {
        ReportGenerator<Derived> reportGenerator = new XLSReportGenerator<>();
        List<Derived> entities = new ArrayList<>();
        entities.add(new Derived());

        var report = reportGenerator.generate(entities, "simpleTest");
        var expected = readXLSReport(getTestPath("simpleTest.xls"));
        assertTrue(ReportCompare.isXLSReportsTheSame(expected, report));
    }

    @Test
    public void asBytesTest() {
        workPath = "asBytesTest.xls";
        ReportGenerator<Derived> reportGenerator = new XLSReportGenerator<>();
        List<Derived> entities = new ArrayList<>();
        entities.add(new Derived());

        var report = reportGenerator.generate(entities, "asBytesTest");
        var reportDeserialized = XLSReportFromBytes(report.asBytes());
        assertAll(
                () -> assertTrue(ReportCompare.isXLSReportsTheSame(reportDeserialized, report)),
                () -> assertDoesNotThrow(
                        () -> {
                            try (var fos = new FileOutputStream(workPath)) {
                                fos.write(report.asBytes());
                            }
                        }
                )
        );
    }

    @Test
    public void writeToTest() {
        workPath = "writeToTest.xls";
        ReportGenerator<Derived> reportGenerator = new XLSReportGenerator<>();
        List<Derived> entities = new ArrayList<>();
        entities.add(new Derived());

        var report = reportGenerator.generate(entities, "simpleTest");

        assertAll(
                () -> assertDoesNotThrow(
                        () -> {
                            try (var fos = new FileOutputStream(workPath)) {
                                fos.write(report.asBytes());
                            }
                        }
                ),
                () -> {
                    var expected = readXLSReport(getTestPath("simpleTest.xls"));
                    var result = readXLSReport(workPath);
                    assertTrue(ReportCompare.isXLSReportsTheSame(expected, result));
                }
        );
    }

    @Test
    public void annotationTest() {
        workPath = "annotationTest.xls";
        ReportGenerator<DerivedAnnotated> reportGenerator = new XLSReportGenerator<>();
        List<DerivedAnnotated> entities = new ArrayList<>();
        entities.add(new DerivedAnnotated());

        var report = reportGenerator.generate(entities, "annotationTest");
        var expected = readXLSReport(getTestPath("annotationTest.xls"));

        assertTrue(ReportCompare.isXLSReportsTheSame(expected, report));
    }
}
