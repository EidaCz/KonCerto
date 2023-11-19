package cz.eida.minecraft.koncerto.commands.executors;

import cz.eida.minecraft.koncerto.commands.ECommands;

/**
 * Play command.
 *
 * @author EidaCz
 */
public class CmdPlay extends CommandExecutorAdapter {

    public CmdPlay() {
        super(ECommands.PLAY.getCommandBase());
    }
}
