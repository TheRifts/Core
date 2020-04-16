package me.Lozke.handlers;

import me.Lozke.FallingAutism;
import me.Lozke.data.items.*;
import me.Lozke.data.Tier;
import me.Lozke.utils.NumGenerator;
import me.Lozke.utils.Text;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ItemHandler {

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
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(itemMeta);
        return format(item);
    }

    public static void addAttributes(ItemStack item, AutisticAttribute... attributes) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        HashMap<String, Integer> map = new HashMap<>();
        for (AutisticAttribute attribute : attributes) {
            map.put(attribute.name(), NumGenerator.Roll(attribute.getMinValue(), attribute.getMaxValue()));
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
            AutisticAttribute attribute = attributes[NumGenerator.Roll(attributes.length - 1)];
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
                meta.setDisplayName(Text.colorize("&f" + sb.toString()));
            }
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
}
