package me.Lozke.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Items {

    private static ItemMeta getItemMeta(ItemStack stack) {
        return (stack.getItemMeta() == null) ? Bukkit.getServer().getItemFactory().getItemMeta(stack.getType()) : stack.getItemMeta();
    }

    public static ItemStack formatItem(Material material, String name, String[] lore) {
        return formatItem(new ItemStack(material), name, lore);
    }
    public static ItemStack formatItem(ItemStack stack, String name, String[] lore) {
        ItemMeta im = getItemMeta(stack);
        im.setDisplayName(Text.colorize(name));
        im.setLore(Arrays.asList(lore));
        stack.setItemMeta(im);
        return stack;
    }

    public static ItemStack formatItem(Material material, String name) {
        return formatItem(new ItemStack(material), name);
    }
    public static ItemStack formatItem(ItemStack stack, String name) {
        ItemMeta im = getItemMeta(stack);
        im.setDisplayName(Text.colorize(name));
        stack.setItemMeta(im);
        return stack;
    }

    public static ItemStack setLore(ItemStack stack, String... name) {
        return formatItem(stack, getItemMeta(stack).getDisplayName(), name);
    }

    //Redo This With Packets?
    public static ItemStack makeGlow(ItemStack stack) {
        stack.addUnsafeEnchantment(Enchantment.LUCK, 1);
        ItemMeta im = getItemMeta(stack);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        stack.setItemMeta(im);
        return stack;
    }

    //Redo This With Packets?
    public static ItemStack removeGlow(ItemStack stack) {
        stack.removeEnchantment(Enchantment.LUCK);
        return stack;
    }

    public static ItemStack setCustomModelData(ItemStack stack, int value) {
        ItemMeta im = stack.getItemMeta();
        im.setCustomModelData(value);
        stack.setItemMeta(im);
        return stack;
    }
}
