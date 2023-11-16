package cz.eida.minecraft.koncerto.record;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Record database.
 *
 * @author EidaCz
 */
public class RecordProvider extends YamlConfiguration {

    private final String records = "records.yml";
    private Plugin plugin;

    public RecordProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    public void reloadDatabase() {
        try {
            load(new File(plugin.getDataFolder(), records));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
