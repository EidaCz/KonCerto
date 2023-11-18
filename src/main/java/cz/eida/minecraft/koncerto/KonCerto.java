package cz.eida.minecraft.koncerto;

import cz.eida.minecraft.koncerto.commands.CommandManager;
import cz.eida.minecraft.koncerto.config.ConfigManager;
import cz.eida.minecraft.koncerto.listeners.ListenerManager;
import cz.eida.minecraft.koncerto.namespace.KonCertoNamespace;
import cz.eida.minecraft.koncerto.record.RecordProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * KonCerto custom audio discs plugin.
 *
 * @author EidaCz
 */
public final class KonCerto extends JavaPlugin {

    @Override
    public void onEnable() {

        // basic configuration setup
        ConfigManager.getInstance().registerConfig(this);
        ConfigManager.getInstance().load();

        // namespace setup
        KonCertoNamespace.registerNamespace(this);

        // command setup
        CommandManager.getInstance().registerCommands(this);

        // event handlers
        ListenerManager.getInstance().registerListeners(this);

        // record database setup
        RecordProvider.getInstance().registerRecordProvider(this);

        if (isSupported_KotatkovyKlub()) {
            this.getLogger().info("Using KotatkovyKlub additional capabilities.");
        }

        if (isSupported_RealisticSeasons()) {
            this.getLogger().info("Using RealisticSeasons additional capabilities.");
        }
    }

    @Override
    public void onDisable() {
    }

    public boolean isSupported_RealisticSeasons() {
        return getServer().getPluginManager().isPluginEnabled("RealisticSeasons");
    }

    public boolean isSupported_KotatkovyKlub() {
        return getServer().getPluginManager().isPluginEnabled("KotatkovyKlub");
    }

    public static void main(String[] args) {
        System.out.println("This is a PaperMC plugin. Copy this jar file into your server plugin directory.");
    }
}
