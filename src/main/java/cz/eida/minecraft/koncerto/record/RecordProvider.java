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
    private Map<Integer, Record> records;

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

        records = new HashMap<>();
        int recordCounter = 0;

        for (ListIterator<Map<?,?>> rec = recordDataSource.getMapList("records").listIterator(); rec.hasNext(); ) {

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

            records.put(recordCounter, record);
            recordCounter++;
        }
    }

    public static RecordProvider getInstance(Plugin plugin) {

        if (recordProvider == null) {
            recordProvider = new RecordProvider(plugin);
        }

        return recordProvider;
    }

    public Record getRandom() {

        if (records.size() == 0) {
            return null;
        }

        Random random = new Random();
        return records.get(random.nextInt(records.size()));
    }

    // TODO ?
    public static Record recordFactory() {
        return null;//return new Record();
    }
}
