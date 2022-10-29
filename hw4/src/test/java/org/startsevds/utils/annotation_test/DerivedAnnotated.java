package org.startsevds.utils.annotation_test;

import org.startsevds.report.annotations.ColumnName;

public class DerivedAnnotated extends BaseAnnotated {

    @ColumnName(name = "E")
    private final String D = "e";

    @ColumnName(name = "A")
    private final String E = "a";
}
