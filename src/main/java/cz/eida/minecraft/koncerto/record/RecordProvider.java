package cz.eida.minecraft.koncerto.record;

import cz.eida.minecraft.koncerto.KonCerto;
import cz.eida.minecraft.koncerto.config.ConfigManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

/**
 * Record database.
 *
 * @author EidaCz
 */
public class RecordProvider {

    /** instance */
    private static RecordProvider recordProvider;
    /** database file */
    private final String recordDataFile = "records.yml";
    /**
     * plugin hook
     */
    private Plugin plugin;
    /** data source */
    private YamlConfiguration recordDataSource;
    /**
     * all records in database
     */
    private Map<String, Record> recordsRegistered;
    /** list of all records */
    private List<String> records;
    /** list of droppable-only records */
    private List<String> droppableRecords;

    /**
     * Blank constructor.
     */
    public RecordProvider() {
    }

    /**
     * RecordProvider singleton.
     *
     * @return RecordProvider instance
     */
    public static RecordProvider getInstance() {

        if (recordProvider == null) {
            recordProvider = new RecordProvider();
        }

        return recordProvider;
    }

    /**
     * Register to plugin and reload record database.
     *
     * @param plugin Plugin
     */
    public void registerRecordProvider(Plugin plugin) {
        this.plugin = plugin;
        reloadDatabase();
    }

    /**
     * Reload records.yml database.
     */
    public void reloadDatabase() {

        File data = new File(plugin.getDataFolder(), recordDataFile);

        recordDataSource = YamlConfiguration.loadConfiguration(data);

        if (!data.exists()) {
            try {
                recordDataSource.createSection("records");
                recordDataSource.save(data);
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Could not save record file.");
            }
        }

        recordsRegistered = new HashMap<>();
        records = new ArrayList<>();
        droppableRecords = new ArrayList<>();

        try {
            for (ListIterator<?> rec = recordDataSource.getObject("records", List.class).listIterator(); rec.hasNext(); ) {

                Map<String, ?> internalData = (Map<String, ?>) rec.next();
                Record record = new Record(
                        (KonCerto) plugin,
                        (String) internalData.get("namespace"),
                        (Integer) internalData.get("model"),
                        (Boolean) internalData.get("drop"),
                        (String) internalData.get("title"),
                        (String) internalData.get("author"),
                        (List<String>) internalData.get("lore")
                );


                recordsRegistered.put(record.getNamespace(), record);
                records.add(record.getNamespace());

                if ((Boolean) internalData.get("drop")) {
                    droppableRecords.add(record.getNamespace());
                }
            }
        } catch (NullPointerException e) {
            // TODO no records
        }

        // display disc count to console
        if (count() > 0) {
            plugin.getLogger().info(
                    ConfigManager.getInstance()
                            .getMessages().get("records.loaded")
                            .toString()
                            .replaceAll("__COUNT__", Integer.toString(count()))
            );
        }
    }

    /**
     * Get total count of registered records.
     *
     * @return count
     */
    public int count() {
        return (records != null) ? records.size() : 0;
    }

    /**
     * Get record by its namespace.
     *
     * @param namespace record namespace
     * @return Record
     */
    public Record get(String namespace) {

        return null;
    }

    /**
     * Get random record.
     *
     * @return a record
     */
    public Record getRandom() {
        return getRandom(false);
    }

    /**
     * Get random record, or droppable only record.
     *
     * @param droppableOnly search only within droppable ones
     * @return a record
     */
    public Record getRandom(Boolean droppableOnly) {
        if (!droppableOnly && records.size() == 0) {
            return null;
        }

        if (droppableOnly && droppableRecords.size() == 0) {
            return null;
        }

        Random random = new Random();
        return recordsRegistered.get((droppableOnly) ? droppableRecords.get(random.nextInt(droppableRecords.size())) : records.get(random.nextInt(records.size())));
    }
}
