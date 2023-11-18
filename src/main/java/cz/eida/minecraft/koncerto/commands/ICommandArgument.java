package cz.eida.minecraft.koncerto.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Command argument interface.
 *
 * @author EidaCz
 */
public interface ICommandArgument {

    /**
     * Command name
     *
     * @return command name
     */
    String getName();

    /**
     * Tab-completion.
     *
     * @param argument passed argument stub
     * @param sender   sender
     * @return list of possible values
     */
    List<String> onComplete(String argument, CommandSender sender);

}
