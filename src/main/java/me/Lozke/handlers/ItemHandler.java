package me.Lozke.handlers;

import me.Lozke.FallingAutism;
import me.Lozke.data.Rarity;
import me.Lozke.data.items.*;
import me.Lozke.data.Tier;
import me.Lozke.utils.NumGenerator;
import me.Lozke.utils.Text;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ItemHandler {

    public static ItemStack newHelmet(Tier tier, Rarity rarity) {
        return createItem(tier, rarity, tier.getArmourMaterial(), "_HELMET");
    }

    public static ItemStack newChestplate(Tier tier, Rarity rarity) {
        return createItem(tier, rarity, tier.getArmourMaterial(), "_CHESTPLATE");
    }

    public static ItemStack newLeggings(Tier tier, Rarity rarity) {
        return createItem(tier, rarity, tier.getArmourMaterial(), "_LEGGINGS");
    }

    public static ItemStack newBoots(Tier tier, Rarity rarity) {
        return createItem(tier, rarity, tier.getArmourMaterial(), "_BOOTS");
    }

    //lol this would be the perfect place to return a Set<ItemStack>... just saying...
    public static ItemStack[] newSet(Tier tier, Rarity rarity) {
        return new ItemStack[]{newHelmet(tier, rarity), newChestplate(tier, rarity), newLeggings(tier, rarity), newBoots(tier, rarity)};
    }

    public static ItemStack getWeapon(Tier tier, Rarity rarity, String type) {
        return createItem(tier, rarity, tier.getWeaponMaterial(), "_" + type.toUpperCase());
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

    private static ItemStack createItem(Tier tier, Rarity rarity, String material, String itemType) {
        ItemStack item = null;
        ItemMeta itemMeta = null;
        PersistentDataContainer dataContainer = null;
        switch (itemType) {
            case "_HELMET":
            case "_CHESTPLATE":
            case "_LEGGINGS":
            case "_BOOTS":
                item = new ItemStack(Material.valueOf(material + itemType));
                addAttributes(item, getRandomAttributes(ItemType.Armour, 8));
                itemMeta = item.getItemMeta();
                dataContainer = itemMeta.getPersistentDataContainer();
                dataContainer.set(NamespacedKeys.HP, PersistentDataType.INTEGER, new Random().nextInt(FallingAutism.getGearData().getInt("Helmet.LO")));
                break;
            case "_SWORD":
            case "_AXE":
            case "_SHOVEL":
            case "_HOE":
                item = new ItemStack(Material.valueOf(material + itemType));
                addAttributes(item, getRandomAttributes(ItemType.Weapon, 8));
                itemMeta = item.getItemMeta();
                dataContainer = itemMeta.getPersistentDataContainer();
                dataContainer.set(NamespacedKeys.DMG, PersistentDataType.INTEGER, 5000);
                break;
        }
        dataContainer.set(NamespacedKeys.realItem, PersistentDataType.STRING, "Certified RetardRealms™ Item");
        dataContainer.set(NamespacedKeys.tier, PersistentDataType.STRING, tier.name());
        dataContainer.set(NamespacedKeys.rarity, PersistentDataType.STRING, rarity.name());
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(itemMeta);
        return format(item);
    }

    public static void addAttributes(ItemStack item, AutisticAttribute... attributes) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        HashMap<String, Integer> map = new HashMap<>();
        for (AutisticAttribute attribute : attributes) {
            map.put(attribute.name(), NumGenerator.rollInclusive(attribute.getMinValue(), attribute.getMaxValue()));
        }
        dataContainer.set(NamespacedKeys.attributes, NamespacedKeys.MAP_PERSISTENT_DATA_TYPE, map);
        item.setItemMeta(meta);
    }

    public static AutisticAttribute[] getRandomAttributes(ItemType itemType, int amount) {
        ArrayList<AutisticAttribute> randomAttributes = new ArrayList();
        AutisticAttribute[] attributes = new AutisticAttribute[0];
        if (itemType.equals(ItemType.Armour)) {
            attributes = AutisticAttribute.armourValues;
        }
        if (itemType.equals(ItemType.Weapon)) {
            attributes = AutisticAttribute.weaponValues;
        }
        while (randomAttributes.size() < amount && attributes.length > 0) {
            AutisticAttribute attribute = attributes[NumGenerator.index(attributes.length)];
            if (!randomAttributes.contains(attribute)) {
                randomAttributes.add(attribute);
            }
        }
        return randomAttributes.toArray(new AutisticAttribute[0]);
    }

    //Thanks stackoverflow!
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static ItemStack format(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        List<String> list = new ArrayList<>();
        if (dataContainer.has(NamespacedKeys.realItem, PersistentDataType.STRING)) {
            if (dataContainer.has(NamespacedKeys.HP, PersistentDataType.INTEGER)) {
                String statColor = percentageToColor((double)dataContainer.get(NamespacedKeys.HP, PersistentDataType.INTEGER) / FallingAutism.getGearData().getInt("Helmet.LO"));
                list.add(Text.colorize("&7HP: " + statColor + "+" + dataContainer.get(NamespacedKeys.HP, PersistentDataType.INTEGER)));
            }
            if (dataContainer.has(NamespacedKeys.DMG, PersistentDataType.INTEGER)) {
                String statColor = percentageToColor((double)dataContainer.get(NamespacedKeys.DMG, PersistentDataType.INTEGER) / 5000);
                list.add(Text.colorize("&7DMG" + statColor + "+"  + dataContainer.get(NamespacedKeys.DMG, PersistentDataType.INTEGER)));
            }
            list.add("");
            if (dataContainer.has(NamespacedKeys.attributes, NamespacedKeys.MAP_PERSISTENT_DATA_TYPE)) {
                Map map = sortByValue(dataContainer.get(NamespacedKeys.attributes, NamespacedKeys.MAP_PERSISTENT_DATA_TYPE));
                StringBuilder sb = new StringBuilder();
                for (Object key : map.keySet()) {
                    String loreDisplay = AutisticAttribute.valueOf(String.valueOf(key)).getLoreDisplayName();
                    String itemName = AutisticAttribute.valueOf(String.valueOf(key)).getItemDisplayName();
                    String statColor = percentageToColor((double)(int)map.get(key) / AutisticAttribute.valueOf(String.valueOf(key)).getMaxValue());
                    list.add(Text.colorize("&7" + loreDisplay.replace("{value}", statColor + "+" + map.get(key))));
                    if (!itemName.equalsIgnoreCase("")) {
                        sb.append(itemName + " ");
                    }
                }
                sb.append(meta.getDisplayName());
                meta.setDisplayName(Text.colorize(getTier(item).getColorCode() + sb.toString()));
            }
            list.add(Text.colorize(getRarity(item).getColorCode() + "&l" + getRarity(item).name()));
        }
        meta.setLore(list);
        item.setItemMeta(meta);
        return item;
    }

    private static String percentageToColor(double percentage) {
        if (percentage == 1) {
            return "&a&l";
        }
        else if (percentage >= 0.75) {
            return "&e";
        }
        else if (percentage >= 0.25) {
            return "&6";
        }
        else {
            return "&c";
        }
    }

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

    public static Tier getTier(ItemStack item) {
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        if (dataContainer.has(NamespacedKeys.realItem, PersistentDataType.STRING)) {
            String data = dataContainer.get(NamespacedKeys.tier, PersistentDataType.STRING);
            for (Tier tier : Tier.types) {
                if (tier.name().equalsIgnoreCase(data)) {
                    return tier;
                }
            }
        }
        return null;
    }

    public static Rarity getRarity(ItemStack item) {
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        if (dataContainer.has(NamespacedKeys.realItem, PersistentDataType.STRING)) {
            String data = dataContainer.get(NamespacedKeys.rarity, PersistentDataType.STRING);
            for (Rarity rarity : Rarity.types) {
                if (rarity.name().equalsIgnoreCase(data)) {
                    return rarity;
                }
            }
        }
        return null;
    }
}
