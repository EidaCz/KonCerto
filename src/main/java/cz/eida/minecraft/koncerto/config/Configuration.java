package cz.eida.minecraft.koncerto.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author EidaCz
 */
public class Configuration extends YamlConfiguration {

    private File configurationFile;

    public Configuration(File configurationFile) throws IOException, InvalidConfigurationException {
        this.configurationFile = configurationFile;
        reload();
    }

    public void reload() throws IOException, InvalidConfigurationException {
        load(configurationFile);
    }


}
