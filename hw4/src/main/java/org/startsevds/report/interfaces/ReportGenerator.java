package org.startsevds.report.interfaces;

import java.util.List;

public interface ReportGenerator<T> {
    Report generate(List<T> entities, String reportName);
}
