package cz.eida.minecraft.koncerto.commands;

import cz.eida.minecraft.koncerto.commands.executors.CmdGive;
import cz.eida.minecraft.koncerto.commands.executors.CmdPlay;
import cz.eida.minecraft.koncerto.commands.executors.CmdPlayAt;
import cz.eida.minecraft.koncerto.commands.executors.CmdStop;
import org.bukkit.plugin.Plugin;

/**
 * Command manager.
 *
 * @author EidaCz
 */
public class CommandManager {

    /**
     * instance
     */
    private static CommandManager commandManager;

    /**
     * Get singleton.
     *
     * @return this instance
     */
    public static CommandManager getInstance() {

        if (commandManager == null) {
            commandManager = new CommandManager();
        }

        return commandManager;
    }

    /**
     * Register commands to plugin.
     *
     * @param plugin KonCerto plugin
     */
    public void registerCommands(Plugin plugin) {

        new CmdGive().registerCommand(plugin);
        new CmdPlay().registerCommand(plugin);
        new CmdPlayAt().registerCommand(plugin);
        new CmdStop().registerCommand(plugin);

    }
}
