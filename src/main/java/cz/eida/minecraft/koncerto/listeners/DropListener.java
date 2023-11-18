package cz.eida.minecraft.koncerto.listeners;

import cz.eida.minecraft.koncerto.record.RecordProvider;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Creeper drop event listener.
 *
 * @author EidaCz
 */
public class DropListener implements Listener {

    private Plugin plugin;

    public DropListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreeperDeath(final EntityDeathEvent event) {

        // creeper only
        if (!(event.getEntity() instanceof Creeper)) return;

        List<ItemStack> drops = event.getDrops();

        // disc dropped?
        ItemStack disc = null;
        for (ItemStack item : drops) {
            if (item.getType().isRecord()) {
                disc = item;

                // replace disc with custom record
                // TODO implement probabilty and seasonal settings
                drops.remove(item);
                disc = RecordProvider.getInstance().getRandom(true).getItem();
                drops.add(disc);

                break;
            }
        }

        // not dropped
        if (disc == null) return;

    }


}
