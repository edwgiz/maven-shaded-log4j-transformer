package org.apache.logging.log4j.shadedTranformer;

import com.github.edwgiz.mavenShadePlugin.log4j2CacheTransformer.PluginsCacheFileTransformer;
import org.junit.Test;

import java.io.InputStream;

import static org.apache.logging.log4j.core.config.plugins.processor.PluginProcessor.PLUGIN_CACHE_FILE;

public class PluginsCacheFileTransformerTest {

    @Test
    public void test() throws Exception {
        PluginsCacheFileTransformer t = new PluginsCacheFileTransformer();
        final InputStream is = getClass().getClassLoader().getResourceAsStream(PLUGIN_CACHE_FILE);
        t.processResource(PLUGIN_CACHE_FILE, is, null);
    }
}