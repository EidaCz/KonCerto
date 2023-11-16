package cz.eida.minecraft.koncerto.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;


/**
 *
 *
 * @author EidaCz
 */
public class MusicStreamListener extends PacketAdapter implements Listener {

    private Plugin plugin;

    public MusicStreamListener(Plugin plugin, ListenerPriority priority) {
        super(plugin, priority, new PacketType[] { PacketType.Play.Server.WORLD_EVENT });
    }

}
