package me.Lozke.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
}
