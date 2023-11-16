package cz.eida.minecraft.koncerto.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author EidaCz
 */
public class Messages extends YamlConfiguration {

    private File messagesFile;

    public Messages(File messagesFile) throws IOException, InvalidConfigurationException {
        this.messagesFile = messagesFile;
        reload();
    }

    public void reload() throws IOException, InvalidConfigurationException {
        load(messagesFile);
    }
}
