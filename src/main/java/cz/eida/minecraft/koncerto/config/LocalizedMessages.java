package cz.eida.minecraft.koncerto.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author EidaCz
 */
public class LocalizedMessages extends YamlConfiguration {

    private File messagesFile;

    public LocalizedMessages(File messagesFile) throws IOException, InvalidConfigurationException {
        this.messagesFile = messagesFile;
        reload();
    }

    public void reload() throws IOException, InvalidConfigurationException {
        load(messagesFile);
    }
}
