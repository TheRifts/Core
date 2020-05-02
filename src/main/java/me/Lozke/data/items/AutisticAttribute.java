package me.Lozke.data.items;

import java.util.ArrayList;

public enum AutisticAttribute {
    VIT(ItemType.ARMOR, "VIT: +{value}", "", 1, 1, 5),
    STR(ItemType.ARMOR, "STR: +{value}", "", 1, 1, 5),
    DEX(ItemType.ARMOR, "DEX: +{value}", "", 1, 1, 5),
    INT(ItemType.ARMOR, "INT: +{value}", "", 1, 1, 5),
    BLOCK(ItemType.ARMOR, "BLOCK: +{value}%", "Protective", 1, 1, 5),
    DODGE(ItemType.ARMOR, "DODGE: +{value}%", "Agile", 1, 1, 5),
    REFLECTION(ItemType.ARMOR, "REFLECTION: +{value}%", "Reflective", 1, 1, 5),
    THORNS(ItemType.ARMOR, "THORNS: +{value}%", "Spiked", 1, 1, 5),
    ELEMENTAL_RESISTANCE(ItemType.ARMOR, "ELEMENTAL RESISTANCE: +{value}%", "Elemental Resistant", 1, 1, 5),
    GEM_FIND(ItemType.ARMOR, "GEM FIND: +{value}%", "", 1, 1, 5),
    ITEM_FIND(ItemType.ARMOR, "ITEM FIND: +{value}%", "", 1, 1, 5),
    VS_PLAYER(ItemType.WEAPON, "vs. PLAYERS: +{value}%", "Slaying", 1, 1, 5),
    VS_MONSTER(ItemType.WEAPON, "vs. MONSTERS: +{value}%", "Slaughter", 1, 1, 5),
    FIRE(ItemType.WEAPON, "FIRE DMG: +{value}", "Fire", 1, 1, 5),
    ICE(ItemType.WEAPON, "ICE DMG: +{value}", "Ice", 1, 1, 5),
    POISON(ItemType.WEAPON, "POISON DMG: +{value}", "Poison", 1, 1, 5),
    PURE(ItemType.WEAPON, "PURE DMG: +{value}", "Pure", 1, 1, 5),
    CRIT_HIT(ItemType.WEAPON, "CRITICAL HIT: +{value}%", "Deadly", 1, 1, 5),
    BLUNT_HIT(ItemType.WEAPON, "BLUNT HIT: +{value}%", "Blunt", 1, 1, 5),
    ARMOR_PEN(ItemType.WEAPON, "ARMOR PENETRATION: +{value}%", "Penetrating", 1, 1, 5),
    SLOWNESS(ItemType.WEAPON, "SLOW: +{value}%", "Snaring", 1, 1, 5),
    ACCURACY(ItemType.WEAPON, "ACCURACY: +{value}%", "Accurate", 1, 1, 5),
    BLINDNESS(ItemType.WEAPON, "BLIND: +{value}%", "Blinding", 1, 1, 5),
    LIFE_STEAL(ItemType.WEAPON, "LIFE STEAL: +{value}%", "Vampyric", 1, 1, 5);


    public static final AutisticAttribute[] allValues = AutisticAttribute.values();
    public static final AutisticAttribute[] armourValues = getArmorValues();
    public static final AutisticAttribute[] weaponValues = getWeaponValues();

    private static AutisticAttribute[] getArmorValues() {
        ArrayList<AutisticAttribute> array = new ArrayList();
        for (AutisticAttribute attribute : allValues) {
            if (attribute.getItemType().equals(ItemType.ARMOR)) {
                array.add(attribute);
            }
        }
        return array.toArray(new AutisticAttribute[0]);
    }
    private static AutisticAttribute[] getWeaponValues() {
        ArrayList<AutisticAttribute> array = new ArrayList();
        for (AutisticAttribute attribute : allValues) {
            if (attribute.getItemType().equals(ItemType.WEAPON)) {
                array.add(attribute);
            }
        }
        return array.toArray(new AutisticAttribute[0]);
    }

    private final ItemType itemType;
    private final String loreDisplayName;
    private final String itemDisplayName;
    private final int displayWeight;
    private final int minValue;
    private final int maxValue;


    AutisticAttribute(ItemType itemType, String loreDisplayName, String itemDisplayName, int displayWeight, int minValue, int maxValue) {
        this.itemType = itemType;
        this.loreDisplayName = loreDisplayName;
        this.itemDisplayName = itemDisplayName;
        this.displayWeight = displayWeight;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }


    public ItemType getItemType() {
        return itemType;
    }
    public String getLoreDisplayName() {
        return loreDisplayName;
    }
    public String getItemDisplayName() {
        return itemDisplayName;
    }
    public int getDisplayWeight() {
        return displayWeight;
    }
    public int getMinValue() {
        return minValue;
    }
    public int getMaxValue() {
        return maxValue;
    }
}