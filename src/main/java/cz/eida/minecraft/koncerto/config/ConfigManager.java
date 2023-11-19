package cz.eida.minecraft.koncerto.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Configuration manager.
 *
 * @author EidaCz
 */
public class ConfigManager {

    /** manager instance */
    private static ConfigManager configManager;

    /** plugin */
    private Plugin plugin;

    /** global configuration */
    private Configuration configuration;
    /** global translation */
    private LocalizedMessages messages;

    public ConfigManager() {

    }

    public void registerConfig(Plugin plugin) {
        this.plugin = plugin;

        if (!plugin.getDataFolder().exists()) {
            this.initialize();
        }

        try {
            this.configuration = new Configuration(new File(this.plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        // determine path
        File messageFile = new File(this.plugin.getDataFolder(), "messages." + configuration.get("lang") + ".yml");

        if (!messageFile.exists()) {
            // fallback
            messageFile = new File(this.plugin.getDataFolder(), "messages.en.yml");
        }

        try {
            this.messages = new LocalizedMessages(messageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConfigManager getInstance() {

        if (configManager == null) {
            configManager = new ConfigManager();
        }

        return configManager;
    }

    public void load() {
        if (!plugin.getDataFolder().exists()) {
            this.initialize();
        }
    }

    public void save() {
        // TODO
    }

    public void initialize() {
        plugin.getDataFolder().mkdirs();
        plugin.saveResource(new File(this.plugin.getDataFolder(), "config.yml").getName(), true);
        plugin.saveResource(new File(this.plugin.getDataFolder(), "messages.en.yml").getName(), true);
        plugin.saveResource(new File(this.plugin.getDataFolder(), "messages.cs.yml").getName(), true);
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public YamlConfiguration getMessages() {
        return this.messages;
    }

}
