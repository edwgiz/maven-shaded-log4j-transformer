package com.github.edwgiz.maven_shade_plugin.log4j2_cache_transformer;

import org.apache.logging.log4j.core.config.plugins.processor.PluginCache;
import org.apache.logging.log4j.core.config.plugins.processor.PluginEntry;
import org.apache.maven.plugins.shade.relocation.Relocator;
import org.apache.maven.plugins.shade.relocation.SimpleRelocator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.enumeration;
import static java.util.Collections.singletonList;
import static org.apache.logging.log4j.core.config.plugins.processor.PluginProcessor.PLUGIN_CACHE_FILE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


final class PluginsCacheFileTransformerTest {

    private static URL pluginUrl;

    @BeforeAll
    public static void setUp() {
        pluginUrl = PluginsCacheFileTransformerTest.class.getClassLoader().getResource(PLUGIN_CACHE_FILE);
    }


    @Test
    public void test() throws Exception {
        final PluginsCacheFileTransformer transformer = new PluginsCacheFileTransformer();
        try (InputStream log4jCacheFileInputStream = getClass().getClassLoader()
                .getResourceAsStream(PLUGIN_CACHE_FILE)) {
            transformer.processResource(PLUGIN_CACHE_FILE, log4jCacheFileInputStream, null);
            assertFalse(transformer.hasTransformedResource());

            final List<Relocator> relocators = new ArrayList<>();
            relocators.add(new SimpleRelocator(null, null, null, null));
            transformer.processResource(PLUGIN_CACHE_FILE, log4jCacheFileInputStream, relocators);
        }
        assertTrue(transformer.hasTransformedResource());
    }


    @Test
    public void testRelocation() throws IOException {
        // test with matching relocator
        testRelocation("org.apache.logging", "new.location.org.apache.logging", "new.location.org.apache.logging");

        // test without matching relocator
        testRelocation("com.apache.logging", "new.location.com.apache.logging", "org.apache.logging");
    }

    private void testRelocation(final String src, final String pattern, final String target) throws IOException {
        final PluginsCacheFileTransformer transformer = new PluginsCacheFileTransformer();
        final Relocator log4jRelocator = new SimpleRelocator(src, pattern, null, null);
        final PluginCache aggregator = new PluginCache();
        aggregator.loadCacheFiles(enumeration(singletonList(pluginUrl)));

        transformer.relocatePlugin(singletonList(log4jRelocator), aggregator.getAllCategories());

        for (final Map<String, PluginEntry> pluginEntryMap : aggregator.getAllCategories().values()) {
            for (final PluginEntry entry : pluginEntryMap.values()) {
                assertTrue(entry.getClassName().startsWith(target));
            }
        }
    }

    @AfterAll
    public static void tearDown() {
        pluginUrl = null;
    }
}