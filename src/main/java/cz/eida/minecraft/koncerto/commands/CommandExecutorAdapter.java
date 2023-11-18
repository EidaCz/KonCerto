package cz.eida.minecraft.koncerto.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic command executor adapter.
 *
 * @author EidaCz
 */
public class CommandExecutorAdapter implements CommandExecutor, TabCompleter {

    /**
     * base command
     */
    private final String BASE = "koncerto";

    /**
     * sub command
     */
    private final String command;
    /**
     * list of arguments
     */
    private final List<CommandArgument> arguments;

    public CommandExecutorAdapter(String command) {
        this.command = BASE + "." + command;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(CommandArgument argument) throws IllegalArgumentException {
        if (arguments.size() > 0) {
            final CommandArgument prevArg = arguments.get(arguments.size() - 1);

            // argument mandatory/optional order check
            if (!prevArg.isMandatory() && argument.isMandatory()) {
                throw new IllegalArgumentException("Incorrect argument order.");
            }

        }

        arguments.add(argument);
    }

    boolean executePlayer(Player sender, String[] args) {
        return true;
    }

    boolean executeCommand(CommandSender sender, String[] args) {
        return true;
    }

    void registerCommand(Plugin plugin) {
        final PluginCommand pluginCommand = plugin.getServer()
                .getPluginCommand(this.command);
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);

        // TODO usage help
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // permission test
        if (!command.testPermissionSilent(sender)) {
            // TODO insufficient permission
            return true;
        }

        // more arguments
        if (args.length > arguments.size()) {
            return false;
        }

        // less arguments
        if (args.length < arguments.size()) {

            // require arguments
            if (args.length == 0 || arguments.get(args.length - 1).isMandatory()) {
                if (arguments.get(args.length).isMandatory()) {
                    return false;
                }
            }

        }

        // execute
        return (sender instanceof Player) ? executePlayer((Player) sender, args) : executeCommand(sender, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            final int index = args.length - 1;
            final CommandArgument argument = arguments.get(index);
            return argument.onComplete(args[index], sender);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
