package com.github.edwgiz.mavenShadePlugin.log4j2CacheTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.core.config.plugins.processor.PluginCache;
import org.apache.logging.log4j.core.config.plugins.processor.PluginEntry;
import org.apache.maven.plugins.shade.relocation.Relocator;
import org.apache.maven.plugins.shade.resource.ResourceTransformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import static org.apache.logging.log4j.core.config.plugins.processor.PluginProcessor.PLUGIN_CACHE_FILE;

public class PluginsCacheFileTransformer implements ResourceTransformer {

    private final ArrayList<File> tempFiles = new ArrayList<File>();
    private final List<Relocator> relocators = new ArrayList<Relocator>();

    public boolean canTransformResource(String resource) {
        return resource != null && PLUGIN_CACHE_FILE.equals(resource);
    }


    public void processResource(String resource, InputStream is, List<Relocator> relocators) throws IOException {
        final File tempFile = File.createTempFile("Log4j2Plugins", "dat");
        FileOutputStream fos = new FileOutputStream(tempFile);
        try {
            IOUtils.copyLarge(is, fos);
        } finally {
            IOUtils.closeQuietly(fos);
        }
        tempFiles.add(tempFile);

        if (relocators != null) {
            this.relocators.addAll(relocators);
        }
    }


    public boolean hasTransformedResource() {
        return tempFiles.size() > 1 || (tempFiles.size() > 0 && relocators.size() > 0);
    }


    public void modifyOutputStream(JarOutputStream jos) throws IOException {
        try {
            PluginCache aggregator = new PluginCache();
            aggregator.loadCacheFiles(getUrls());

            relocatePlugin(aggregator, relocators);

            jos.putNextEntry(new JarEntry(PLUGIN_CACHE_FILE));
            aggregator.writeCache(new CloseShieldOutputStream(jos));
        } finally {
            ListIterator<File> it = tempFiles.listIterator();
            while (it.hasNext()) {
                File f = it.next();
                //noinspection ResultOfMethodCallIgnored
                f.delete();
                it.remove();
            }
        }
    }

    void relocatePlugin(PluginCache aggregator, List<Relocator> relocators) {
        for (Map.Entry<String, Map<String, PluginEntry>> categoryEntry : aggregator.getAllCategories().entrySet()) {
            for (Map.Entry<String, PluginEntry> pluginMapEntry : categoryEntry.getValue().entrySet()) {
                PluginEntry pluginEntry = pluginMapEntry.getValue();
                String originalClassName = pluginEntry.getClassName();

                Relocator matchingRelocator = findFirstMatchingRelocator(originalClassName, relocators);

                if (matchingRelocator != null) {
                    String newClassName = matchingRelocator.relocateClass(originalClassName);
                    pluginEntry.setClassName(newClassName);
                }
            }
        }
    }

    private Relocator findFirstMatchingRelocator(String originalClassName, List<Relocator> relocators) {
        for (Relocator relocator : relocators) {
            if (relocator.canRelocateClass(originalClassName)) {
                return relocator;
            }
        }
        return null;
    }


    private Enumeration<URL> getUrls() throws MalformedURLException {
        List<URL> urls = new ArrayList<URL>();
        for (File tempFile : tempFiles) {
            final URL url = tempFile.toURI().toURL();
            urls.add(url);
        }
        return Collections.enumeration(urls);
    }
}
