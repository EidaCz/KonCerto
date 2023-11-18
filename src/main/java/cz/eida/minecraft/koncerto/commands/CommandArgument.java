package cz.eida.minecraft.koncerto.commands;

/**
 * Argument abstract.
 *
 * @author EidaCz
 */
public abstract class CommandArgument implements ICommandArgument {

    /**
     * mandatory flag
     */
    private boolean mandatory;

    public CommandArgument(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * Argument is mandatory in command.
     *
     * @return is mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * Set argument as mandatory.
     *
     * @param mandatory settings
     */
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
}
