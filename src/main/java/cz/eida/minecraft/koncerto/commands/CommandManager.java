package cz.eida.minecraft.koncerto.commands;

import org.bukkit.plugin.Plugin;

/**
 * Command manager.
 *
 * @author EidaCz
 */
public class CommandManager {

    private static CommandManager commandManager;

    public void registerCommands(Plugin plugin) {
    }

    public static CommandManager getInstance() {

        if (commandManager == null) {
            commandManager = new CommandManager();
        }

        return commandManager;
    }

}
