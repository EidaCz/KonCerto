package cz.eida.minecraft.koncerto.record;

import cz.eida.minecraft.koncerto.KonCerto;
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

    private Plugin plugin;

    private static RecordProvider recordProvider;

    private final String recordDataFile = "records.yml";
    private YamlConfiguration recordDataSource;
    private List<Record> records;
    private List<Record> droppableRecords;

    public RecordProvider(Plugin plugin) {
        this.plugin = plugin;
        reloadDatabase();
    }

    public void reloadDatabase() {

        File data = new File(plugin.getDataFolder(), recordDataFile);

        recordDataSource = YamlConfiguration.loadConfiguration(data);

        if (!data.exists()) {
            try {
                recordDataSource.save(data);
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Could not save record file.");
            }
        }

        records = new ArrayList<>();
        droppableRecords = new ArrayList<>();

        for (ListIterator<?> rec = recordDataSource.getObject("records", List.class).listIterator(); rec.hasNext(); ) {

            Map<String,?> internalData = (Map<String,?>) rec.next();
            Record record = new Record(
                    (KonCerto) plugin,
                    (String) internalData.get("namespace"),
                    (Integer) internalData.get("model"),
                    (Boolean) internalData.get("drop"),
                    (String) internalData.get("title"),
                    (String) internalData.get("author"),
                    (List<String>) internalData.get("lore")
            );

            records.add(record);

            if ((Boolean) internalData.get("drop")) {
                droppableRecords.add(record);
            }

            plugin.getLogger().info(record.toString());
        }
    }

    public static RecordProvider getInstance(Plugin plugin) {

        if (recordProvider == null) {
            recordProvider = new RecordProvider(plugin);
        }

        return recordProvider;
    }

    public Record getRandom() {
        return getRandom(false);
    }

    public Record getRandom(Boolean droppableOnly) {
        if (!droppableOnly && records.size() == 0) {
            return null;
        }

        if (droppableOnly && droppableRecords.size() == 0) {
            return null;
        }

        Random random = new Random();
        return (droppableOnly) ? droppableRecords.get(random.nextInt(droppableRecords.size())) : records.get(random.nextInt(records.size()));
    }
}
