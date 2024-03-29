package cz.eida.minecraft.koncerto.record;

import cz.eida.minecraft.koncerto.KonCerto;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Disc record representation.
 *
 * @author EidaCz
 */
public class Record {

    /** disk base material = Music Disc 11 */
    public static final Material BASE_DISC = Material.MUSIC_DISC_11;
    /**
     * plugin hook
     */
    private KonCerto plugin;


    /** disc namespace */
    private String namespace;
    /** disc namespaced key */
    private NamespacedKey nsk;
    /** disc CustomModelData ID, defaults to 1000 */
    private Integer model = 1000;
    /** disc can be dropped by Creeper, defaults to true */
    private boolean drop = true;
    /** disc title */
    private String title;
    /** disc author */
    private String author;
    /** disc custom lore, multiple lines */
    private List<String> lore;

    // TODO probability settings
    // TODO RealisticSeasons settings

    /**
     * Blank constructor.
     */
    public Record() {
    }

    /**
     * Full constructor.
     *
     * @param namespace disc namespace
     * @param model custom model data ID
     * @param drop droppable by creepers
     * @param title disc title
     * @param author disc author
     * @param lore custom lore lines
     */
    public Record(KonCerto plugin,
                  String namespace,
                  @Nullable Integer model,
                  @Nullable Boolean drop,
                  String title,
                  String author,
                  @Nullable List<String> lore) {

        // plugin hook
        this.plugin = plugin;

        // register namespace
        this.namespace = namespace;
        this.nsk = new NamespacedKey(this.plugin, namespace);

        // retain default values
        if (model != null) this.model = model;
        if (drop != null) this.drop = drop;

        this.title = title;
        this.author = author;
        this.lore = lore;
    }

    /**
     * Simplified constructor.
     *
     * @param plugin plugin instance
     * @param namespace disc namespace
     * @param title disc title
     * @param author disc author
     */
    public Record(KonCerto plugin, String namespace, String title, String author) {
        this(plugin, namespace, null, null, title, author, null);
    }

    public String getNamespace() {
        return namespace;
    }

    public Integer getModel() {
        return model;
    }

    public boolean isDroppable() {
        return drop;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getLore() {
        return lore;
    }

    /**
     * Return lore lines as Component list.
     *
     * @return Component list of lore lines
     */
    public List<Component> getLoreComponent() {
        List<Component> lore = new ArrayList<>();
        for (String loreLine : this.lore) {
            lore.add(Component.text(loreLine));
        }
        return lore;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
        this.nsk = new NamespacedKey(this.plugin, namespace);
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public void setDroppable(boolean drop) {
        this.drop = drop;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    /**
     * Get disc as item.
     *
     * @return custom disc a item
     */
    public ItemStack getItem() {

        if (plugin.isSupported_KotatkovyKlub()) {
            // TODO - simplified item generation
        }

        ItemStack discItem = new ItemStack(BASE_DISC, 1);
        ItemMeta discMeta = discItem.getItemMeta();

        discMeta.setCustomModelData(this.model);
        discMeta.displayName(Component.text(this.author + " - " + this.title));
        discMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

        if (this.lore != null) {
            discMeta.lore(getLoreComponent());
        }

        discItem.setItemMeta(discMeta);

        return discItem;
    }

    /**
     * Debugging lines.
     *
     * @return record info
     */
    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();

        info.append("Record");
        info.append(" { ");
        info.append("namespace=" + getNamespace());
        info.append(", ");
        info.append("author=" + getAuthor());
        info.append(", ");
        info.append("title=" + getTitle());
        info.append(", ");
        info.append("model=" + getModel());
        info.append(", ");
        info.append("droppable=" + isDroppable());
        info.append(", ");
        info.append("lore=" + getLore());
        info.append(", ");
        info.append("item=" + getItem().toString());
        info.append(" } ");

        return info.toString();
    }
}
