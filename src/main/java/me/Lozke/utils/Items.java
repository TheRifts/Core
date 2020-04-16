package me.Lozke.utils;

import me.Lozke.data.items.NamespacedKeys;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class Items {

    public static ItemStack formatItem(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(Text.colorize(name));
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack formatItem(ItemStack item, String name) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(Text.colorize(name));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack makeGlow(ItemStack item) {
        item.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        ItemMeta im = item.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);
        return item;
    }

    public static boolean isRealItem(ItemStack item) {
        if (item != null) {
            if(item.hasItemMeta()) {
                PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
                if (container.has(NamespacedKeys.realItem, PersistentDataType.STRING)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isMeleeWeapon(ItemStack item) {
        String name = item.getType().name();
        if(name.contains("SWORD") || name.contains("AXE") || name.contains("HOE") || name.contains("SHOVEL")) {
            return true;
        }
        return false;
    }
}
