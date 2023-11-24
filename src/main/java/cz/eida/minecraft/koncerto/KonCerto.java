package cz.eida.minecraft.koncerto;

import cz.eida.minecraft.koncerto.commands.CommandManager;
import cz.eida.minecraft.koncerto.config.ConfigManager;
import cz.eida.minecraft.koncerto.config.ResourcePackReader;
import cz.eida.minecraft.koncerto.listeners.ListenerManager;
import cz.eida.minecraft.koncerto.namespace.KonCertoNamespace;
import cz.eida.minecraft.koncerto.namespace.KonCertoNotifier;
import cz.eida.minecraft.koncerto.record.RecordProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * KonCerto custom audio discs plugin.
 *
 * @author EidaCz
 */
public final class KonCerto extends JavaPlugin {

    /**
     * Program main entry point.
     * Prints notification.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("This is a PaperMC plugin. Copy this jar file into your server plugin directory.");
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {

        // basic configuration setup
        ConfigManager.getInstance().registerConfig(this);
        ConfigManager.getInstance().load();

        // notifier
        KonCertoNotifier.getInstance().register(this);

        // namespace setup
        KonCertoNamespace.registerNamespace(this);

        // command setup
        CommandManager.getInstance().registerCommands(this);

        // event handlers
        ListenerManager.getInstance().registerListeners(this);

        // record database setup
        RecordProvider.getInstance().registerRecordProvider(this);

        // resource pack reader
        ResourcePackReader.getInstance().register(this);

        if (isSupported_KotatkovyKlub()) {
            KonCertoNotifier.getInstance().getLogger().info("Using KotatkovyKlub additional capabilities.");
        }

        if (isSupported_RealisticSeasons()) {
            KonCertoNotifier.getInstance().getLogger().info("Using RealisticSeasons additional capabilities.");
        }
    }

    /**
     * Detect additional support for plugin
     * RealisticSeasons
     *
     * @return support status
     */
    public boolean isSupported_RealisticSeasons() {
        return getServer().getPluginManager().isPluginEnabled("RealisticSeasons");
    }

    /**
     * Detect additional support for plugin
     * KotatkovyKlub
     *
     * @return support status
     */
    public boolean isSupported_KotatkovyKlub() {
        return getServer().getPluginManager().isPluginEnabled("KotatkovyKlub");
    }
}
