package cz.eida.minecraft.koncerto.listeners;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Event listener manager.
 *
 * @author EidaCz
 */
public class ListenerManager {

    private static ListenerManager listenerManager;

    public ListenerManager() {
    }

    public void registerListeners(Plugin plugin) {

        PluginManager pluginManager = plugin.getServer().getPluginManager();

        // event listeners
        pluginManager.registerEvents(new ResourcepackStatusListener(plugin), plugin);
        pluginManager.registerEvents(new DropListener(plugin), plugin);
        pluginManager.registerEvents(new JukeboxListener(plugin), plugin);
    }

    public static ListenerManager getInstance() {

        if (listenerManager == null) {
            listenerManager = new ListenerManager();
        }

        return listenerManager;
    }

}
