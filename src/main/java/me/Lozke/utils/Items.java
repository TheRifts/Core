package me.Lozke.utils;

import me.Lozke.data.Tier;
import me.Lozke.data.items.NamespacedKeys;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class Items {
    private static final int noWeaponEnergy = 5;
    private static final int weaponEnergy = 8;

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
        if (item != null && item.hasItemMeta()) {
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            return container.has(NamespacedKeys.realItem, PersistentDataType.STRING);
        }
        return false;
    }

    public static boolean isTiered(ItemStack item) {
        if (isRealItem(item)) {
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            if(container.has(NamespacedKeys.tier, PersistentDataType.STRING));
        }
        return false;
    }

    public static boolean isWeapon(ItemStack item) {
        if (isRealItem(item)) {
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            return container.has(NamespacedKeys.DMG, PersistentDataType.INTEGER);
        }
        return false;
    }

    public static int getTier(ItemStack item) {
        if (isRealItem(item) && isTiered(item)) {
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            return Tier.valueOf(container.get(NamespacedKeys.tier, PersistentDataType.STRING)).getTierNumber();
        }
        return 0;
    }

    public static float getItemEnergyCost(ItemStack item) {
        if (Items.isRealItem(item) && Items.isWeapon(item)) {
            return weaponEnergy+Items.getTier(item);
        }
        else {
            return noWeaponEnergy;
        }
    }
}
