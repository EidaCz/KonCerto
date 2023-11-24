package cz.eida.minecraft.koncerto.commands.executors;

import cz.eida.minecraft.koncerto.commands.ECommands;
import cz.eida.minecraft.koncerto.commands.arguments.ArgumentPlayer;
import cz.eida.minecraft.koncerto.commands.arguments.ArgumentRecord;
import cz.eida.minecraft.koncerto.commands.selectors.PlayerSelector;
import cz.eida.minecraft.koncerto.commands.selectors.Selectors;
import cz.eida.minecraft.koncerto.record.Record;
import cz.eida.minecraft.koncerto.record.RecordPlayer;
import cz.eida.minecraft.koncerto.record.RecordProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Stop playing command.
 * /koncerto.stop record_namespace player_selector
 *
 * @author EidaCz
 */
public class CmdStop extends CommandExecutorAdapter {
    public CmdStop() {
        super(ECommands.STOP.getCommandBase());
        addArgument(new ArgumentRecord(true));
        addArgument(new ArgumentPlayer(true));
    }

    @Override
    boolean executePlayer(Player sender, String[] args) {
        return execute(sender, args);
    }

    @Override
    boolean executeCommand(CommandSender sender, String[] args) {
        return execute(sender, args);
    }

    boolean execute(CommandSender sender, String[] args) {

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

        RecordPlayer recordPlayer = new RecordPlayer(record);

        for (Player player : players) {
            recordPlayer.stop(player);
        }

        return true;
    }


}
