package org.startsevds.utils.annotation_test;

import org.startsevds.report.annotations.ColumnName;

public class BaseAnnotated {
    private final String D = "d";

    @ColumnName(name = "C")
    private final String B = "c";

    @ColumnName(name = "B")
    private final String C = "b";
}
