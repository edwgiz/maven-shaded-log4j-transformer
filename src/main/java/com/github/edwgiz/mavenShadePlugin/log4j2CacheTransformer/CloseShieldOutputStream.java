package com.github.edwgiz.mavenShadePlugin.log4j2CacheTransformer;


import org.apache.commons.io.output.ProxyOutputStream;

import java.io.IOException;
import java.io.OutputStream;

import static org.apache.commons.io.output.ClosedOutputStream.CLOSED_OUTPUT_STREAM;

public class CloseShieldOutputStream extends ProxyOutputStream {

    public CloseShieldOutputStream(OutputStream out) {
        super(out);
    }


    @Override
    public void close() throws IOException {
        out.flush();
        out = CLOSED_OUTPUT_STREAM;
    }
}
