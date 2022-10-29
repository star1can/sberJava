package org.startsevds.report.interfaces;

import java.io.IOException;
import java.io.OutputStream;


public interface Report {
    byte[] asBytes();

    void writeTo(OutputStream os);
}
