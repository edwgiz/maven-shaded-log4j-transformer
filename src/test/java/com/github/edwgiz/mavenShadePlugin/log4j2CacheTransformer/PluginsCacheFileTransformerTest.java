package com.github.edwgiz.mavenShadePlugin.log4j2CacheTransformer;

import org.apache.logging.log4j.core.config.plugins.processor.PluginCache;
import org.apache.logging.log4j.core.config.plugins.processor.PluginEntry;
import org.apache.maven.plugins.shade.relocation.Relocator;
import org.apache.maven.plugins.shade.relocation.SimpleRelocator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import static java.util.Collections.enumeration;
import static java.util.Collections.singletonList;
import static org.apache.logging.log4j.core.config.plugins.processor.PluginProcessor.PLUGIN_CACHE_FILE;
import static org.junit.Assert.assertTrue;


public class PluginsCacheFileTransformerTest {

    private static URL pluginUrl;

    @BeforeClass
    public static void setUp() {
        pluginUrl = PluginsCacheFileTransformerTest.class.getClassLoader().getResource(PLUGIN_CACHE_FILE);
    }

    @Test
    public void test() throws Exception {
        PluginsCacheFileTransformer t = new PluginsCacheFileTransformer();
        final InputStream is = getClass().getClassLoader().getResourceAsStream(PLUGIN_CACHE_FILE);
        t.processResource(PLUGIN_CACHE_FILE, is, null);

        assertTrue(t.hasTransformedResource());
    }


    @Test
    public void testRelocation() throws IOException {
        // test with matching relocator
        testRelocation("org.apache.logging", "new.location.org.apache.logging", "new.location.org.apache.logging");

        // test without matching relocator
        testRelocation("com.apache.logging", "new.location.com.apache.logging", "org.apache.logging");
    }

    private void testRelocation(String src, String pattern, String target) throws IOException {
        PluginsCacheFileTransformer t = new PluginsCacheFileTransformer();
        Relocator log4jRelocator = new SimpleRelocator(src, pattern, null, null);
        PluginCache aggregator = new PluginCache();
        aggregator.loadCacheFiles(enumeration(singletonList(pluginUrl)));

        t.relocatePlugin(aggregator, singletonList(log4jRelocator));

        for (Map<String, PluginEntry> pluginEntryMap : aggregator.getAllCategories().values()) {
            for (PluginEntry entry : pluginEntryMap.values()) {
                assertTrue(entry.getClassName().startsWith(target));
            }
        }
    }

    @AfterClass
    public static void tearDown() {
        pluginUrl = null;
    }
}