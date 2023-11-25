package cz.eida.minecraft.koncerto.commands.selectors;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Location selector helper.
 *
 * @author EidaCz
 */
public class LocationSelector {

    private Integer X, Y, Z;
    private Player player;

    public LocationSelector(String x, String y, String z, Player player) {
        this.player = player;

        if (x.equals(Selectors.RELATIVE.getSymbol())) {
            X = player.getLocation().getBlockX();
        } else {
            X = Integer.parseInt(x);
        }

        if (y.equals(Selectors.RELATIVE.getSymbol())) {
            Y = player.getLocation().getBlockY();
        } else {
            Y = Integer.parseInt(x);
        }

        if (z.equals(Selectors.RELATIVE.getSymbol())) {
            Z = player.getLocation().getBlockZ();
        } else {
            Z = Integer.parseInt(x);
        }
    }

    public Location getLocation() {
        return new Location(player.getWorld(), X, Y, Z);
    }

}
