package cz.eida.minecraft.koncerto.commands;

import cz.eida.minecraft.koncerto.commands.selectors.Selectors;
import cz.eida.minecraft.koncerto.record.RecordProvider;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Argument for record namespace.
 *
 * @author EidaCz
 */
public class ArgRecord extends CommandArgument {

    public ArgRecord(Boolean mandatory) {
        super(mandatory);
    }

    @Override
    public String getName() {
        return "record namespace";
    }

    @Override
    public List<String> onComplete(String argument, CommandSender sender) {

        List<String> list = RecordProvider.getInstance().list();

        if (list.size() > 0) {
            list.add(Selectors.RAND.getSymbol());
        }

        List<String> found = new ArrayList<>();
        for (final String discNamespace : list) {
            if (discNamespace.toLowerCase().startsWith(argument.toLowerCase())) {
                found.add(discNamespace);
            }
        }

        return (found.size() > 0) ? found : null;
    }
}
