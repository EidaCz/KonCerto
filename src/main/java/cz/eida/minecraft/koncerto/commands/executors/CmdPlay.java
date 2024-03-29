package cz.eida.minecraft.koncerto.commands.executors;

import cz.eida.minecraft.koncerto.commands.ECommands;
import cz.eida.minecraft.koncerto.commands.arguments.ArgumentPlayer;
import cz.eida.minecraft.koncerto.commands.arguments.ArgumentRecord;
import cz.eida.minecraft.koncerto.commands.arguments.ArgumentValue;
import cz.eida.minecraft.koncerto.commands.selectors.PlayerSelector;
import cz.eida.minecraft.koncerto.commands.selectors.Selectors;
import cz.eida.minecraft.koncerto.record.Record;
import cz.eida.minecraft.koncerto.record.RecordPlayer;
import cz.eida.minecraft.koncerto.record.RecordProvider;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Play command.
 * /koncerto.play record_namespace player_selector (pitch) (volume)
 *
 * @author EidaCz
 */
public class CmdPlay extends CommandExecutorAdapter {

    public CmdPlay() {
        super(ECommands.PLAY.getCommandBase());
        addArgument(new ArgumentRecord(true));
        addArgument(new ArgumentPlayer(true));
        addArgument(new ArgumentValue(false, 0.5f, 1.0f, 1.5f).setName("pitch"));
        addArgument(new ArgumentValue(false, 4.0f, 1.0f, 0.5f).setName("volume"));
    }

    @Override
    boolean executePlayer(Player sender, String[] args) {
        return execute(sender, args);
    }

    @Override
    boolean executeCommand(CommandSender sender, String[] args) {
        return execute(sender, args);
    }

    private boolean execute(CommandSender sender, String[] args) {
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

        // player
        final List<Player> players = new PlayerSelector(sender, args[1]).getSelection();
        if (players == null) {
            return true;
        }

        // pitch
        float pitch = 1.0f;
        if (args.length >= 3) {
            try {
                pitch = Float.parseFloat(args[2]);
            } catch (NumberFormatException e) {
                // TODO format
                return true;
            }
        }

        // volume
        float volume = 1.0f;
        if (args.length >= 4) {
            try {
                volume = Float.parseFloat(args[3]);
            } catch (NumberFormatException e) {
                // TODO format
                return true;
            }
        }

        // record player
        RecordPlayer recordPlayer = new RecordPlayer(record).setPitch(pitch).setVolume(volume);

        // play to selected players
        for (Player player : players) {
            player.playSound(player.getLocation(), record.getNamespace(), SoundCategory.RECORDS, volume, pitch);
        }

        // TODO notify

        return true;
    }
}
