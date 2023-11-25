package cz.eida.minecraft.koncerto.commands.arguments;

import cz.eida.minecraft.koncerto.commands.selectors.Selectors;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Argument for a location.
 *
 * @author EidaCz
 */
public class ArgumentLocation extends CommandArgument {

    private Axis axis;

    ;

    /**
     * Constructor for single axis.
     *
     * @param mandatory mandatory argument
     * @param axis      axis (X, Y, or Z)
     */
    public ArgumentLocation(Boolean mandatory, Axis axis) {
        super(mandatory);
        this.axis = axis;
    }

    @Override
    public String getName() {
        return this.axis.toString().toLowerCase();
    }

    @Override
    public List<String> onComplete(String argument, CommandSender sender) {

        // console or player
        final Player player = (sender instanceof Player) ? (Player) sender : null;
        if (player == null) {
            return null;
        }

        final List<String> suggestions = new ArrayList<>();
        // current position
        suggestions.add(Selectors.RELATIVE.getSymbol());

        // targeted block?
        final Block block = player.getTargetBlockExact(4);
        if (block == null) {
            return suggestions;
        }

        final Location location = block.getLocation();

        switch (axis) {
            case X -> {
                Integer x = location.getBlockX();
                suggestions.add(x.toString());
            }

            case Y -> {
                Integer y = location.getBlockY();
                suggestions.add(y.toString());
            }

            case Z -> {
                Integer z = location.getBlockZ();
                suggestions.add(z.toString());
            }

            default -> {
            }
        }

        return suggestions;
    }

    public enum Axis {X, Y, Z}
}
