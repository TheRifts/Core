package me.Lozke.handlers;

import me.Lozke.RetardRealms;
import me.Lozke.data.items.NamespacedKeys;
import me.Lozke.data.Tier;
import me.Lozke.data.items.Orb;
import me.Lozke.data.items.Scrap;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class ItemHandler {

    public static void handleStats(Player player, ItemStack item, boolean equipped) {
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        if (dataContainer.has(NamespacedKeys.realItem, PersistentDataType.STRING)) {
            AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            int itemHP = dataContainer.get(NamespacedKeys.HP, PersistentDataType.INTEGER);
            if (equipped) {
                maxHealth.setBaseValue((int) maxHealth.getValue() + itemHP);
            }
            else {
                maxHealth.setBaseValue((int) maxHealth.getValue() - itemHP);
                if (player.getHealth() > maxHealth.getValue()) {
                    try {
                        player.setHealth(player.getHealth() - itemHP);
                    } catch (IllegalArgumentException exception) {
                        player.setHealth(maxHealth.getValue());
                    }
                }
            }
        }
    }

    private static ItemStack createItem(String material, String itemType) {
        ItemStack item = null;
        ItemMeta itemMeta = null;
        PersistentDataContainer dataContainer = null;
        switch (itemType) {
            case "_HELMET":
            case "_CHESTPLATE":
            case "_LEGGINGS":
            case "_BOOTS":
                item = new ItemStack(Material.valueOf(material + itemType));
                itemMeta = item.getItemMeta();
                dataContainer = itemMeta.getPersistentDataContainer();
                dataContainer.set(NamespacedKeys.HP, PersistentDataType.INTEGER, new Random().nextInt(RetardRealms.getGearData().getInt("Helmet.LO")));
                break;
            case "_SWORD":
            case "_AXE":
            case "_SHOVEL":
            case "_HOE":
                item = new ItemStack(Material.valueOf(material + itemType));
                itemMeta = item.getItemMeta();
                dataContainer = itemMeta.getPersistentDataContainer();
                dataContainer.set(NamespacedKeys.DMG, PersistentDataType.INTEGER, 5000);
                break;
        }
        dataContainer.set(NamespacedKeys.realItem, PersistentDataType.STRING, "Certified RetardRealms™ Item");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack newHelmet(Tier tier) {
        return createItem(tier.getArmourMaterial(), "_HELMET");
    }

    public static ItemStack newChestplate(Tier tier) {
        return createItem(tier.getArmourMaterial(), "_CHESTPLATE");
    }

    public static ItemStack newLeggings(Tier tier) {
        return createItem(tier.getArmourMaterial(), "_LEGGINGS");
    }

    public static ItemStack newBoots(Tier tier) {
        return createItem(tier.getArmourMaterial(), "_BOOTS");
    }

    //lol this would be the perfect place to return a Set<ItemStack>... just saying...
    public static ItemStack[] newSet(Tier tier) {
        return new ItemStack[]{newHelmet(tier), newChestplate(tier), newLeggings(tier), newBoots(tier)};
    }

    public static ItemStack getWeapon(Tier tier, String type) {
        return createItem(tier.getWeaponMaterial(), "_" + type.toUpperCase());
    }

    public static ItemStack newScrap(Tier tier) {
        return newScrap(tier, 1);
    }

    public static ItemStack newScrap(Tier tier, int amount) {
        ItemStack scrap = Scrap.types[tier.ordinal()].getItem();
        scrap.setAmount(amount);
        return scrap;
    }

    public static ItemStack newOrb(Tier tier) {
        return newOrb(tier, 1);
    }

    public static ItemStack newOrb(Tier tier, int amount) {
        ItemStack orb = Orb.types[tier.ordinal()].getItem();
        orb.setAmount(amount);
        return orb;
    }
}
