package mcheroes.core.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemStackBuilder {
    private final Material material;
    private Component name = null;
    private List<Component> lore = null;

    private ItemStackBuilder(Material material) {
        this.material = material;
    }

    public static ItemStackBuilder of(Material material) {
        return new ItemStackBuilder(material);
    }

    public ItemStackBuilder name(Component name) {
        this.name = name;
        return this;
    }

    public ItemStackBuilder lore(List<Component> lore) {
        this.lore = lore;
        return this;
    }

    public ItemStack build() {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();

        if (name != null) meta.displayName(name);
        if (lore != null) meta.lore(lore);

        item.setItemMeta(meta);
        return item;
    }
}
