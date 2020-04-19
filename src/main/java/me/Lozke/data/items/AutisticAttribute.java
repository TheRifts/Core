package me.Lozke.data.items;

import java.util.ArrayList;

public enum AutisticAttribute {

    VIT(ItemType.Armour, "VIT: +{value}", "", 1, 1, 5),
    STR(ItemType.Armour, "STR: +{value}", "", 1, 1, 5),
    DEX(ItemType.Armour, "DEX: +{value}", "", 1, 1, 5),
    INT(ItemType.Armour, "INT: +{value}", "", 1, 1, 5),
    BLOCK(ItemType.Armour, "BLOCK: +{value}%", "Protective", 1, 1, 5),
    DODGE(ItemType.Armour, "DODGE: +{value}%", "Agile", 1, 1, 5),
    REFLECTION(ItemType.Armour, "REFLECTION: +{value}%", "Reflective", 1, 1, 5),
    THORNS(ItemType.Armour, "THORNS: +{value}%", "Spiked", 1, 1, 5),
    ELEMENTAL_RESISTANCE(ItemType.Armour, "ELEMENTAL RESISTANCE: +{value}%", "Elemental Resistant", 1, 1, 5),
    GEM_FIND(ItemType.Armour, "GEM FIND: +{value}%", "", 1, 1, 5),
    ITEM_FIND(ItemType.Armour, "ITEM FIND: +{value}%", "", 1, 1, 5),
    VS_PLAYER(ItemType.Weapon, "vs. PLAYERS: +{value}%", "Slaying", 1, 1, 5),
    VS_MONSTER(ItemType.Weapon, "vs. MONSTERS: +{value}%", "Slaughter", 1, 1, 5),
    FIRE(ItemType.Weapon, "FIRE DMG: +{value}", "Fire", 1, 1, 5),
    ICE(ItemType.Weapon, "ICE DMG: +{value}", "Ice", 1, 1, 5),
    POISON(ItemType.Weapon, "POISON DMG: +{value}", "Poison", 1, 1, 5),
    PURE(ItemType.Weapon, "PURE DMG: +{value}", "Pure", 1, 1, 5),
    CRIT_HIT(ItemType.Weapon, "CRITICAL HIT: +{value}%", "Deadly", 1, 1, 5),
    BLUNT_HIT(ItemType.Weapon, "BLUNT HIT: +{value}%", "Blunt", 1, 1, 5),
    ARMOR_PEN(ItemType.Weapon, "ARMOR PENETRATION: +{value}%", "Penetrating", 1, 1, 5),
    SLOWNESS(ItemType.Weapon, "SLOW: +{value}%", "Snaring", 1, 1, 5),
    ACCURACY(ItemType.Weapon, "ACCURACY: +{value}%", "Accurate", 1, 1, 5),
    BLINDNESS(ItemType.Weapon, "BLIND: +{value}%", "Blinding", 1, 1, 5),
    LIFE_STEAL(ItemType.Weapon, "LIFE STEAL: +{value}%", "Vampyric", 1, 1, 5);

    public static final AutisticAttribute[] allValues = AutisticAttribute.values();
    public static final AutisticAttribute[] armourValues = getArmourValues();
    public static final AutisticAttribute[] weaponValues = getWeaponValues();

    private static AutisticAttribute[] getArmourValues() {
        ArrayList<AutisticAttribute> array = new ArrayList();
        for (AutisticAttribute attribute : allValues) {
            if (attribute.getItemType().equals(ItemType.Armour)) {
                array.add(attribute);
            }
        }
        return array.toArray(new AutisticAttribute[0]);
    }

    private static AutisticAttribute[] getWeaponValues() {
        ArrayList<AutisticAttribute> array = new ArrayList();
        for (AutisticAttribute attribute : allValues) {
            if (attribute.getItemType().equals(ItemType.Weapon)) {
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