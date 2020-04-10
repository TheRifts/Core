package me.Lozke.data.items;

public class Attributes {

    public enum Gear {
        VIT("VIT: +", "", 1, 1, 5),
        STR("STR: +", "", 1, 1, 5),
        DEX("DEX: +", "", 1, 1, 5),
        INT("INT: +", "", 1, 1, 5),
        BLOCK("BLOCK: {value}%", "Protective", 1, 1, 5),
        DODGE("DODGE: {value}%", "Agile", 1, 1, 5),
        REFLECTION("REFLECTION: {value}%", "Reflection", 1, 1, 5),
        THORNS("THORNS: {value}% DMG", "Spiked", 1, 1, 5),
        FIRE_RESISTANCE("FIRE RESISTANCE: {value}%", "Fire Resistant", 1, 1, 5),
        ICE_RESISTANCE("ICE RESISTANCE: {value}%", "Frost Resistant", 1, 1, 5),
        POISON_RESISTANCE("POISON RESISTANCE: {value}%", "Poison Resistant", 1, 1, 5),
        GEM_FIND("GEM FIND: +{value}%", "", 1, 1, 5),
        ITEM_FIND("ITEM FIND: +{value}%", "", 1, 1, 5);

        public static Gear[] types = Gear.values();

        private final String loreDisplayName;
        private final String itemDisplayName;
        private final int displayWeight;
        private final int minValue;
        private final int maxValue;

        Gear(String loreDisplayName, String itemDisplayName, int displayWeight, int minValue, int maxValue) {
            this.loreDisplayName = loreDisplayName;
            this.itemDisplayName = itemDisplayName;
            this.displayWeight = displayWeight;
            this.minValue = minValue;
            this.maxValue = maxValue;
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

    public enum Weapon {
        VS_PLAYER("vs. PLAYERS: +{value}%", "Slaying", 1, 1, 5),
        VS_MONSTER("vs. MONSTERS: +{value}%", "Slaughter", 1, 1, 5),
        FIRE("FIRE DMG: +{value}", "Fire", 1, 1, 5),
        ICE("ICE DMG: +{value}", "Ice", 1, 1, 5),
        POISON("POISON DMG: +{value}", "Poison", 1, 1, 5),
        PURE("PURE DMG: +{value}", "Pure", 1, 1, 5),
        CRIT_HIT("CRITICAL HIT: {value}%", "Deadly", 1, 1, 5),
        BLUNT_HIT("BLUNT HIT: {value}%", "Blunt", 1, 1, 5),
        ARMOR_PEN("ARMOR PENETRATION: {value}%", "Penetrating", 1, 1, 5),
        SLOWNESS("SLOW: {value}%", "Snaring", 1, 1, 5),
        ACCURACY("ACCURACY: {value}%", "Accurate", 1, 1, 5),
        BLINDNESS("BLIND: {value}%", "Blinding", 1, 1, 5),
        LIFE_STEAL("LIFE STEAL: {value}%", "Vampyric", 1, 1, 5);

        public static Weapon[] types = Weapon.values();

        private final String loreDisplayName;
        private final String itemDisplayName;
        private final int displayWeight;
        private final int minValue;
        private final int maxValue;

        Weapon(String loreDisplayName, String itemDisplayName, int displayWeight, int minValue, int maxValue) {
            this.loreDisplayName = loreDisplayName;
            this.itemDisplayName = itemDisplayName;
            this.displayWeight = displayWeight;
            this.minValue = minValue;
            this.maxValue = maxValue;
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
}


