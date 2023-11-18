package cz.eida.minecraft.koncerto.commands;

import cz.eida.minecraft.koncerto.commands.selectors.PlayerSelector;
import cz.eida.minecraft.koncerto.record.Record;
import cz.eida.minecraft.koncerto.record.RecordProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Give player a custom record.
 * <p>
 * /koncerto.give record_namespace [player]
 */
public class CmdGive extends CommandExecutorAdapter {

    public CmdGive() {
        // give
        super(ECommands.GIVE.getCommandBase());
        // record namespace
        addArgument(new ArgRecord(true));
        // [player(s)]
        addArgument(new ArgPlayer(true));
    }

    @Override
    public boolean executePlayer(Player sender, String[] args) {
        return execute(sender, args);
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {

        // console only - target argument is mandatory
        if (args.length < 2) {
            return false;
        }

        return execute(sender, args);
    }

    private boolean execute(CommandSender sender, String[] args) {

        if (args.length == 1 && sender instanceof ConsoleCommandSender) {
            return false;
        }

        String playerName = (args.length == 1) ? ((Player) sender).getName() : args[1];

        // get players
        final List<Player> players = new PlayerSelector(sender, playerName).getSelection();

        // no players found
        if (players == null) {
            return true;
        }

        Record record = RecordProvider.getInstance().get(args[0]);
        if (record == null) {
            // TODO log/message - not found
            return false;
        }

        for (Player p : players) {
            p.getInventory().addItem(record.getItem());
        }

        return true;
    }

}
