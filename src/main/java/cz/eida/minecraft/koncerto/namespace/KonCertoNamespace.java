package cz.eida.minecraft.koncerto.namespace;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author EidaCz
 */
public enum KonCertoNamespace {
    NAMESPACE_ID("koncerto.namespace_id"),
    ID("koncerto.id"),
    AUTHOR("koncerto.author"),
    TITLE("koncerto.title");

    private String key;
    private NamespacedKey namespacedKey;

    KonCertoNamespace(String key) {
        this.key = key;
        this.namespacedKey = null;
    }

    public NamespacedKey get() {
        if (this.namespacedKey == null) {
            throw new NullPointerException("Namespace is not registered.");
        }

        return this.namespacedKey;
    }

    public String getKey() {
        return this.key;
    }

    public static void registerNamespace(Plugin plugin) {
        for (KonCertoNamespace namespace : KonCertoNamespace.values()) {
            namespace.namespacedKey = new NamespacedKey(plugin, namespace.key);
        }
    }

}
