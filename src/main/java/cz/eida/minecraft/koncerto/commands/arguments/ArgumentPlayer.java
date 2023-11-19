package cz.eida.minecraft.koncerto.commands.arguments;

import cz.eida.minecraft.koncerto.commands.selectors.PlayerSelector;
import cz.eida.minecraft.koncerto.commands.selectors.Selectors;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArgumentPlayer extends CommandArgument {

    public ArgumentPlayer(Boolean mandatory) {
        super(mandatory);
    }

    @Override
    public String getName() {
        return "player";
    }

    @Override
    public List<String> onComplete(String argument, CommandSender sender) {

        // all players
        final List<Player> players = new PlayerSelector(sender, argument).getAllServerPlayers();

        // no players found
        if (players.size() == 0) {
            return null;
        }

        List<String> list = new ArrayList<>();

        // all players
        for (Player p : players) {
            if (p.getName().toLowerCase().startsWith(argument.toLowerCase())) {
                list.add(p.getName());
            }
        }

        // all symbols
        for (Selectors s : Selectors.values()) {
            if (s.getTabComplete() && s.getSymbol().toLowerCase().startsWith(argument.toLowerCase())) {

                if (sender instanceof Player || (sender instanceof ConsoleCommandSender && s.getAccessibleFromConsole())) {
                    list.add(s.getSymbol());
                }

            }
        }

        return list;
    }
}
