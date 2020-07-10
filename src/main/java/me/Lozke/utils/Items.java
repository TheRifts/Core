package me.Lozke.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Items {

    public static ItemStack formatItem(Material material, String name, String[] lore) {
        return formatItem(new ItemStack(material), name, lore);
    }
    public static ItemStack formatItem(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(Text.colorize(name));
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack formatItem(Material material, String name) {
        return formatItem(new ItemStack(material), name);
    }
    public static ItemStack formatItem(ItemStack item, String name) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(Text.colorize(name));
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack setLore(ItemStack item, String... name) {
        return formatItem(item, item.getItemMeta().getDisplayName(), name);
    }

    //Redo This With Packets?
    public static ItemStack makeGlow(ItemStack item) {
        item.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        ItemMeta im = item.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);
        return item;
    }

    //Redo This With Packets?
    public static ItemStack removeGlow(ItemStack item) {
        item.removeEnchantment(Enchantment.VANISHING_CURSE);
        return item;
    }
}
