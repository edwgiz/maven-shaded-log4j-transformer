package org.apache.logging.log4j.shadedTranformer;

import com.github.edwgiz.mavenShadePlugin.log4j2CacheTransformer.PluginsCacheFileTransformer;
import org.apache.logging.log4j.core.config.plugins.processor.PluginCache;
import org.apache.logging.log4j.core.config.plugins.processor.PluginEntry;
import org.apache.maven.plugins.shade.relocation.Relocator;
import org.apache.maven.plugins.shade.relocation.SimpleRelocator;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import static java.util.Collections.enumeration;
import static java.util.Collections.singletonList;
import static org.apache.logging.log4j.core.config.plugins.processor.PluginProcessor.PLUGIN_CACHE_FILE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

public class PluginsCacheFileTransformerTest {

    final URL pluginUrl = getClass().getClassLoader().getResource(PLUGIN_CACHE_FILE);

    @Test
    public void test() throws Exception {
        PluginsCacheFileTransformer t = new PluginsCacheFileTransformer();
        final InputStream is = getClass().getClassLoader().getResourceAsStream(PLUGIN_CACHE_FILE);
        t.processResource(PLUGIN_CACHE_FILE, is, null);

        assertThat(t.hasTransformedResource(), is(true));
    }


    @Test
    public void test_relocation() throws Exception {
        PluginsCacheFileTransformer t = new PluginsCacheFileTransformer();
        Relocator log4jRelocator = new SimpleRelocator("org.apache.logging", "new.location.org.apache.logging", null, null);
        PluginCache aggregator = new PluginCache();
        aggregator.loadCacheFiles(enumeration(singletonList(pluginUrl)));


        t.relocatePlugin(aggregator, singletonList(log4jRelocator));

        for (Map<String, PluginEntry> pluginEntryMap : aggregator.getAllCategories().values()) {
            for (PluginEntry entry : pluginEntryMap.values()) {
                assertThat(entry.getClassName(), startsWith("new.location.org.apache.logging"));
            }
        }
    }

    @Test
    public void test_relocation_noMatchingRelocator() throws Exception {
        PluginsCacheFileTransformer t = new PluginsCacheFileTransformer();
        Relocator log4jRelocator = new SimpleRelocator("com.apache.logging", "new.location.com.apache.logging", null, null);
        PluginCache aggregator = new PluginCache();
        aggregator.loadCacheFiles(enumeration(singletonList(pluginUrl)));

        t.relocatePlugin(aggregator, singletonList(log4jRelocator));

        for (Map<String, PluginEntry> pluginEntryMap : aggregator.getAllCategories().values()) {
            for (PluginEntry entry : pluginEntryMap.values()) {
                assertThat(entry.getClassName(), startsWith("org.apache.logging"));
            }
        }
    }
}