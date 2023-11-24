package cz.eida.minecraft.koncerto.record;

import cz.eida.minecraft.koncerto.config.ConfigManager;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Record player.
 *
 * @author EidaCz
 */
public class RecordPlayer {

    private static final double RANGE = 16.0;
    private static final float VOLUME = 4.0f;

    private String recordNamespace;
    private float volume;
    private float pitch;

    public RecordPlayer() {
        // TODO - provider
    }

    public RecordPlayer(Record record) {
        this.recordNamespace = record.getNamespace();
        this.volume = VOLUME;
        this.pitch = 1.0f;
    }

    /**
     * Generic player for given sound namespace.
     *
     * @param soundNamespace
     */
    public RecordPlayer(String soundNamespace) {
        this.recordNamespace = soundNamespace;
        this.volume = VOLUME;
        this.pitch = 1.0f;
    }

    /**
     * Set volume.
     *
     * @param value new volume
     * @return this instance
     */
    public RecordPlayer setVolume(float value) {
        this.volume = value;
        return this;
    }

    /**
     * Set pitch.
     *
     * @param value new pitch
     * @return this instance
     */
    public RecordPlayer setPitch(float value) {
        this.pitch = value;
        return this;
    }

    /**
     * Play namespaced sound at given location.
     *
     * @param loc location
     */
    public void play(Location loc) {
        World world = loc.getWorld();

        // stop if overlapping is not allowed
        if (!ConfigManager.getInstance().getConfiguration().getBoolean("music.overlapping")) {
            stop(loc);
        }

        // play sound
        world.playSound(loc, recordNamespace, SoundCategory.RECORDS, volume, pitch);
    }

    /**
     * Play sound to player.
     *
     * @param player Player
     */
    public void play(Player player) {
        player.playSound(player.getLocation(), recordNamespace, SoundCategory.RECORDS, volume, pitch);
    }

    /**
     * Stop receiving sound at given location.
     *
     * @param loc location
     */
    public void stop(Location loc) {
        // loop players
        for (Player player : loc.getWorld().getPlayers()) {
            if (player.getLocation().distance(loc) <= VOLUME * RANGE) {
                player.stopSound(recordNamespace, SoundCategory.RECORDS);
            }
        }
    }

    /**
     * Stop playing sound to player.
     *
     * @param player Player
     */
    public void stop(Player player) {
        player.stopSound(recordNamespace, SoundCategory.RECORDS);
    }
}
