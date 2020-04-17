package me.Lozke.data;

import org.bukkit.Material;

public enum Tier {
    T1(1, "WHITE", Material.WHITE_CONCRETE,"LEATHER", "WOODEN", "&7"), //Wood/Leather
    T2(2, "LIME", Material.LIME_CONCRETE,"CHAINMAIL", "STONE", "&2"), //Stone/Chain
    T3(3, "LIGHT_BLUE", Material.LIGHT_BLUE_CONCRETE,"IRON", "IRON","&b"), //Steel
    T4(4, "PURPLE", Material.PURPLE_CONCRETE,"DIAMOND","DIAMOND","&5"), //Ivory
    T5(5, "YELLOW", Material.YELLOW_CONCRETE,"GOLDEN","GOLDEN", "&e"); //Heavenly
    //T6 Demonic

    public static Tier[] types = Tier.values();

    private final int tierNumber;
    private final String materialColor;
    private final Material material;
    private final String armourMaterial;
    private final String weaponMaterial;
    private final String colorCode;

    Tier(int tierNumber, String materialColor, Material material, String armourMaterial, String weaponMaterial, String colorCode) {
        this.tierNumber = tierNumber;
        this.materialColor = materialColor;
        this.material = material;
        this.armourMaterial = armourMaterial;
        this.weaponMaterial = weaponMaterial;
        this.colorCode = colorCode;
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

    public String getColorCode() {
        return colorCode;
    }

    public String getArmourMaterial() {
        return armourMaterial;
    }

    public String getWeaponMaterial() {
        return weaponMaterial;
    }
}