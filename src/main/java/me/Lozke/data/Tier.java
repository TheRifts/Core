package me.Lozke.data;

import org.bukkit.Material;

public enum Tier {
    T1(1, "WHITE", Material.WHITE_CONCRETE,"LEATHER", "WOODEN", "&7", "Shoddy", 100), //Wood/Leather
    T2(2, "LIME", Material.LIME_CONCRETE,"CHAINMAIL", "STONE", "&2", "Ordinary", 250), //Stone/Chain
    T3(3, "LIGHT_BLUE", Material.LIGHT_BLUE_CONCRETE,"IRON", "IRON","&b", "Strong", 600), //Steel
    T4(4, "PURPLE", Material.PURPLE_CONCRETE,"DIAMOND","DIAMOND","&5", "Fantastic", 850), //Ivory
    T5(5, "YELLOW", Material.YELLOW_CONCRETE,"GOLDEN","GOLDEN", "&e", "Incredible", 1000), //Heavenly
    T6(6, "BLACK", Material.BLACK_CONCRETE, "NETHERITE", "NETHERITE", "&c", "Super Stronk", 1250); //T6 Demonic

    public static Tier[] types = Tier.values();

    private final int tierNumber;
    private final String materialColor;
    private final Material material;
    private final String armorMaterial;
    private final String weaponMaterial;
    private final String colorCode;
    private final String itemDisplayName;
    private final int maxDurability;

    Tier(int tierNumber, String materialColor, Material material, String armorMaterial, String weaponMaterial, String colorCode, String itemDisplayName, int maxDurability) {
        this.tierNumber = tierNumber;
        this.materialColor = materialColor;
        this.material = material;
        this.armorMaterial = armorMaterial;
        this.weaponMaterial = weaponMaterial;
        this.colorCode = colorCode;
        this.itemDisplayName = itemDisplayName;
        this.maxDurability = maxDurability;
    }

    public int getTierNumber() {
        return tierNumber;
    }
    public String getMaterialColor() {
        return materialColor;
    }
    public Material getMaterial() {
        return material;
    }
    public String getArmorMaterial() {
        return armorMaterial;
    }
    public String getWeaponMaterial() {
        return weaponMaterial;
    }
    public String getColorCode() {
        return colorCode;
    }
    public String getItemDisplayName() {
        return itemDisplayName;
    }
    public int getMaxDurability() {
        return maxDurability;
    }
}