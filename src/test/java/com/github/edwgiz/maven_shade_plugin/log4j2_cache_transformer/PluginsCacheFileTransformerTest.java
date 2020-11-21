package com.github.edwgiz.maven_shade_plugin.log4j2_cache_transformer;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.core.config.plugins.processor.PluginCache;
import org.apache.logging.log4j.core.config.plugins.processor.PluginEntry;
import org.apache.maven.plugins.shade.relocation.Relocator;
import org.apache.maven.plugins.shade.relocation.SimpleRelocator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

import static java.util.Collections.enumeration;
import static java.util.Collections.singletonList;
import static org.apache.logging.log4j.core.config.plugins.processor.PluginProcessor.PLUGIN_CACHE_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


final class PluginsCacheFileTransformerTest {

    private static URL pluginUrl;

    @BeforeAll
    public static void setUp() {
        pluginUrl = PluginsCacheFileTransformerTest.class.getClassLoader().getResource(PLUGIN_CACHE_FILE);
    }


    @Test
    public void test() throws Exception {
        final PluginsCacheFileTransformer transformer = new PluginsCacheFileTransformer();
        long expectedYoungestResourceTime = 1605922127000L; // Sat Nov 21 2020 01:28:47
        try (InputStream log4jCacheFileInputStream = getClass().getClassLoader()
                .getResourceAsStream(PLUGIN_CACHE_FILE)) {
            transformer.processResource(PLUGIN_CACHE_FILE, log4jCacheFileInputStream, null, expectedYoungestResourceTime);
        }
        assertFalse(transformer.hasTransformedResource());

        try (InputStream log4jCacheFileInputStream = getClass().getClassLoader()
                .getResourceAsStream(PLUGIN_CACHE_FILE)) {
            transformer.processResource(PLUGIN_CACHE_FILE, log4jCacheFileInputStream, null, 2000L);
        }
        assertTrue(transformer.hasTransformedResource());

        assertExpectedYoungestResourceTime(transformer, expectedYoungestResourceTime, 1911442937);
    }

    private void assertExpectedYoungestResourceTime(
            @SuppressWarnings("SameParameterValue") PluginsCacheFileTransformer transformer,
            @SuppressWarnings("SameParameterValue") long expectedTime,
            @SuppressWarnings("SameParameterValue") long expectedHash) throws IOException {
        final ByteArrayOutputStream jarBuff = new ByteArrayOutputStream();
        try(final JarOutputStream out = new JarOutputStream(jarBuff)) {
            transformer.modifyOutputStream(out);
        }

        try(JarInputStream in = new JarInputStream(new ByteArrayInputStream(jarBuff.toByteArray()))) {
            for (;;) {
                final JarEntry jarEntry = in.getNextJarEntry();
                if(jarEntry == null) {
                    fail("No expected resource in the output jar");
                } else if(jarEntry.getName().equals(PLUGIN_CACHE_FILE)) {
                    assertEquals(expectedTime, jarEntry.getTime());
                    assertEquals(expectedHash, Arrays.hashCode(IOUtils.toByteArray(in)));
                    break;
                }
            }
        }
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