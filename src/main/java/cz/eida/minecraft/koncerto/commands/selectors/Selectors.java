package cz.eida.minecraft.koncerto.commands.selectors;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic selectors.
 *
 * @author EidaCz
 */
public enum Selectors {

    ALL("@a", true, true),
    SELF("@s", true, false),
    WORLD("@w", true, false),
    RAND("@r", true, true),
    NONE("##", false, false);

    private String symbol;
    private Boolean tabComplete;
    private Boolean accessibleFromConsole;

    Selectors(String symbol, Boolean tabComplete, Boolean accessibleFromConsole) {
        this.symbol = symbol;
        this.tabComplete = tabComplete;
        this.accessibleFromConsole = accessibleFromConsole;
    }

    /**
     * All symbols map.
     *
     * @return full map of selectors accessible by symbols
     */
    public static Map<String, Selectors> all() {
        Map<String, Selectors> map = new HashMap<>();

        for (Selectors s : Selectors.values()) {
            map.put(s.getSymbol(), s);
        }

        return map;
    }

    public String getSymbol() {
        return symbol;
    }

    public Boolean getTabComplete() {
        return tabComplete;
    }

    public Boolean getAccessibleFromConsole() {
        return accessibleFromConsole;
    }

}
