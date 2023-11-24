package cz.eida.minecraft.koncerto.config;

import org.bukkit.plugin.Plugin;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Resource pack reader.
 *
 * @author EidaCz
 */
public class ResourcePackReader {

    /**
     * instance
     */
    public static ResourcePackReader resourcePackReader;

    /**
     * KonCerto plugin
     */
    private Plugin plugin;

    /**
     * list of namespaced records in resourcepack
     */
    private List resourcePackRecords;

    /**
     * Blank constructor.
     */
    public ResourcePackReader() {
    }

    /**
     * Get instance.
     *
     * @return instance
     */
    public static ResourcePackReader getInstance() {

        if (resourcePackReader == null) {
            resourcePackReader = new ResourcePackReader();
        }

        return resourcePackReader;
    }

    /**
     * Register plugin
     *
     * @param plugin KonCerto plugin
     */
    public void register(Plugin plugin) {
        this.plugin = plugin;
        load();
    }

    /**
     * Reload from resourcepack.
     */
    public void reload() {
        this.resourcePackRecords = null;
        load();
    }

    /**
     * Load from resourcepack.
     */
    private void load() {
        if (ConfigManager.getInstance().getConfiguration().getBoolean("resourcepack.records-supported-only")) {
            extractResourcePackRecords();
        }
    }

    /**
     * Register a namespace.
     *
     * @param recordNamespace new record namespace
     */
    private void insertRecord(String recordNamespace) {

        if (this.resourcePackRecords == null) {
            this.resourcePackRecords = new ArrayList<String>();
        }

        if (!this.resourcePackRecords.contains(recordNamespace)) {
            this.resourcePackRecords.add(recordNamespace);
        }
    }

    /**
     * List of resourcepack-supported records.
     *
     * @return list of namespaces
     */
    public List<String> getResourcepackRecords() {
        return this.resourcePackRecords;
    }

    /**
     * Fetch server resourcepack URL.
     *
     * @return resourepack URL in server.properties
     */
    private String getResourcePackURL() {

        String resourcePackURL = null;

        File serverPropertiesFile = new File(this.plugin.getServer().getWorldContainer(), "server.properties");
        Properties properties = new Properties();

        try (FileInputStream propertiesInputStream = new FileInputStream(serverPropertiesFile)) {
            properties.load(propertiesInputStream);
            resourcePackURL = properties.getProperty("resource-pack");
        } catch (IOException e) {
            // TODO e.printStackTrace();
        }

        return resourcePackURL;
    }

    /**
     * Find all record namespaces inside remote resourcepack file.
     */
    private void extractResourcePackRecords() {

        // server resourcepack URL
        final String rpURL = getResourcePackURL();
        if (rpURL == null) {
            return;
        }

        // search this file only
        final String soundsFilePath = "assets/minecraft/sounds.json";
        // search only entries pointing to records/
        final String soundsPrefix = "records/";

        // open URL
        try (InputStream urlStream = new URL(rpURL).openStream()) {

            // open ZIP file
            try (ZipInputStream rpStream = new ZipInputStream(urlStream)) {

                ZipEntry entry;
                while ((entry = rpStream.getNextEntry()) != null) {
                    // sounds.json
                    if (entry.getName().equals(soundsFilePath)) {

                        JSONObject sounds = new JSONObject(new String(rpStream.readAllBytes()));
                        Iterator<String> soundsIterator = sounds.keys();

                        // list all namespaces
                        while (soundsIterator.hasNext()) {
                            // namespace supported (records/)
                            String recordNamespace = soundsIterator.next();
                            if (sounds.getJSONObject(recordNamespace).get("sounds").toString().contains(soundsPrefix)) {
                                insertRecord(recordNamespace);
                            }
                        }

                    } // sounds.json file
                } // iterator
            } catch (IOException e) {
                // TODO throw new RuntimeException(e);
            }

        } catch (MalformedURLException e) {
            // TODO throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO throw new RuntimeException(e);
        }
    }
}
