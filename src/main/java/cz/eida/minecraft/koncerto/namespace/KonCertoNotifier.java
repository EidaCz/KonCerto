package cz.eida.minecraft.koncerto.namespace;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

/**
 * Notifier.
 *
 * @author EidaCz
 */
public class KonCertoNotifier {

    /**
     * instance
     */
    private static KonCertoNotifier konCertoNotifier;
    /**
     * KonCerto plugin
     */
    private Plugin plugin;

    /**
     * Blank constructor.
     */
    public KonCertoNotifier() {
    }

    /**
     * Get instance.
     *
     * @return Notifier
     */
    public static KonCertoNotifier getInstance() {

        if (konCertoNotifier == null) {
            konCertoNotifier = new KonCertoNotifier();
        }

        return konCertoNotifier;
    }

    /**
     * Register notifier.
     *
     * @param plugin KonCerto plugin
     */
    public void register(Plugin plugin) {
        this.plugin = plugin;
    }

    public void sendMsg(Object... data) {
        // TODO
    }

    public void sendMsg(CommandSender receiver, Object... data) {
        // TODO
    }

    /**
     * Get plugin internal logger.
     *
     * @return plugin Logger
     */
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

}
