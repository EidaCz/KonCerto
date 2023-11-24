package cz.eida.minecraft.koncerto.commands.arguments;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Value (integer/float) argument.
 *
 * @author EidaCz
 */
public class ArgumentValue extends CommandArgument {

    /**
     * suggested values
     */
    private final List<String> suggestions;
    /**
     * name of this argument
     */
    private String valueName;

    /**
     * Constructor for suggested integers.
     *
     * @param mandatory mandatory argument
     * @param values    suggested integers
     */
    public ArgumentValue(Boolean mandatory, Integer... values) {
        super(mandatory);

        this.suggestions = new ArrayList<>();
        for (Integer value : values) {
            this.suggestions.add(value.toString());
        }
    }

    /**
     * Constructor for suggested floats.
     *
     * @param mandatory mandatory argument
     * @param values    suggested floats
     */
    public ArgumentValue(Boolean mandatory, Float... values) {
        super(mandatory);

        this.suggestions = new ArrayList<>();
        for (Float value : values) {
            this.suggestions.add(value.toString());
        }
    }

    @Override
    public String getName() {
        return (this.valueName != null) ? this.valueName : "number value";
    }

    /**
     * Set name for this value.
     *
     * @param name custom name
     */
    public void setName(String name) {
        this.valueName = name;
    }

    @Override
    public List<String> onComplete(String argument, CommandSender sender) {
        if (argument.isEmpty()) {
            return this.suggestions;
        }
        return null;
    }
}
