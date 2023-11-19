package cz.eida.minecraft.koncerto.commands;

/**
 * KonCerto commands base.
 *
 * @author EidaCz
 */
public enum ECommands {

    BASE("koncerto"),
    GIVE("give"),
    PLAY("play"),
    NONE("");

    private String commandBase;

    // TODO
    private String usage;

    ECommands(String commandBase) {
        this.commandBase = commandBase;
    }

    public String getCommandBase() {
        return commandBase;
    }
}
