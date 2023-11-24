package cz.eida.minecraft.koncerto.listeners;

import cz.eida.minecraft.koncerto.config.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Player resourcepack status listener.
 *
 * @author EidaCz
 */
public class ResourcepackStatusListener implements Listener {

    private Plugin plugin;

    public ResourcepackStatusListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onResourceStatus(PlayerResourcePackStatusEvent e) {

        // schedule kick
        new BukkitRunnable() {
            @Override
            public void run() {

                if (e.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED)
                        && ConfigManager.getInstance().getConfiguration().getBoolean("resourcepack.player-force-kick")) {
                    e.getPlayer().kickPlayer(ConfigManager.getInstance().getMessages().getString("resourcepack.declined"));
                    return;
                }

                if (e.getStatus().equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)
                        && !ConfigManager.getInstance().getConfiguration().getBoolean("resourcepack.player-ignore-if-failed")) {
                    e.getPlayer().kickPlayer(ConfigManager.getInstance().getMessages().getString("resourcepack.failed"));
                    return;
                }

            }
        }.runTaskLater(plugin, 120);
    }

}
