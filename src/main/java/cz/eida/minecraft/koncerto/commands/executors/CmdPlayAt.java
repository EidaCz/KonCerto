package cz.eida.minecraft.koncerto.commands.executors;

import cz.eida.minecraft.koncerto.commands.ECommands;
import cz.eida.minecraft.koncerto.commands.arguments.ArgumentLocation;
import cz.eida.minecraft.koncerto.commands.arguments.ArgumentRecord;
import cz.eida.minecraft.koncerto.commands.arguments.ArgumentValue;
import cz.eida.minecraft.koncerto.commands.selectors.LocationSelector;
import cz.eida.minecraft.koncerto.commands.selectors.Selectors;
import cz.eida.minecraft.koncerto.record.Record;
import cz.eida.minecraft.koncerto.record.RecordPlayer;
import cz.eida.minecraft.koncerto.record.RecordProvider;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Play music record at given location.
 * <p>
 * /koncerto.play record_namespace X Y Z [pitch] [volume]
 *
 * @author EidaCz
 */
public class CmdPlayAt extends CommandExecutorAdapter {

    public CmdPlayAt() {
        super(ECommands.PLAY_AT.getCommandBase());
        addArgument(new ArgumentRecord(true));
        addArgument(new ArgumentLocation(true, ArgumentLocation.Axis.X));
        addArgument(new ArgumentLocation(true, ArgumentLocation.Axis.Y));
        addArgument(new ArgumentLocation(true, ArgumentLocation.Axis.Z));
        addArgument(new ArgumentValue(false, 0.5f, 1.0f, 1.5f).setName("pitch"));
        addArgument(new ArgumentValue(false, 4.0f, 1.0f, 0.5f).setName("volume"));
    }

    /**
     * Executable by player only.
     *
     * @param sender Player
     * @param args   params
     * @return
     */
    @Override
    boolean executePlayer(Player sender, String[] args) {

        // record
        final Record record;

        if (args[0].contains(Selectors.RAND.getSymbol())) {
            record = RecordProvider.getInstance().getRandom();
        } else {
            record = RecordProvider.getInstance().get(args[0]);
        }

        if (record == null) {
            // TODO record not found
            return true;
        }

        final Location location = new LocationSelector(args[1], args[2], args[3], (Player) sender).getLocation();

        // pitch
        float pitch = 1.0f;
        if (args.length >= 5) {
            try {
                pitch = Float.parseFloat(args[4]);
            } catch (NumberFormatException e) {
                // TODO format
                return true;
            }
        }

        // volume
        float volume = 1.0f;
        if (args.length >= 6) {
            try {
                volume = Float.parseFloat(args[5]);
            } catch (NumberFormatException e) {
                // TODO format
                return true;
            }
        }

        // record player
        RecordPlayer recordPlayer = new RecordPlayer(record).setPitch(pitch).setVolume(volume);

        // play at given location
        recordPlayer.play(location);

        return true;
    }
}
