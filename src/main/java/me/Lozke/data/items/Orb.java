package me.Lozke.data.items;

import me.Lozke.data.Tier;
import me.Lozke.utils.Text;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;

public enum Orb {
    T1(),
    T2(),
    T3(),
    T4(),
    T5(),
    T6();

    public static Orb[] types = Orb.values();

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.getPersistentDataContainer().set(NamespacedKeys.realItem, PersistentDataType.INTEGER, 0);
        String colorCode = Tier.valueOf(this.name()).getColorCode();
        itemMeta.setDisplayName(Text.colorize(colorCode + this.name() + " Orb of Alteration"));
        itemMeta.setLore(Collections.singletonList(Text.colorize("&7Place on equipment to " + colorCode + "&nrandomize&7 all bonus stats")));

        item.setItemMeta(itemMeta);
        return item;
    }
}
