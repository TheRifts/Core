package me.Lozke.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
public enum Tier {
    T1(1, "WHITE", Material.WHITE_CONCRETE,"LEATHER", "WOODEN", "&7", "Shoddy", 2000), //Wood/Leather
    T2(2, "LIME", Material.LIME_CONCRETE,"CHAINMAIL", "STONE", "&2", "Ordinary", 2000), //Stone/Chain
    T3(3, "LIGHT_BLUE", Material.LIGHT_BLUE_CONCRETE,"IRON", "IRON","&b", "Strong", 2000), //Steel
    T4(4, "PURPLE", Material.PURPLE_CONCRETE,"DIAMOND","DIAMOND","&5", "Fantastic", 2000), //Ivory
    T5(5, "YELLOW", Material.YELLOW_CONCRETE,"GOLDEN","GOLDEN", "&e", "Incredible", 2000), //Heavenly
    T6(6, "BLACK", Material.BLACK_CONCRETE, "NETHERITE", "NETHERITE", "&c", "Super Stronk", 2000); //T6 Demonic

    public static Tier[] types = Tier.values();

    @Getter private final int tierNumber;
    @Getter private final String materialColor;
    @Getter private final Material material;
    @Getter private final String armourMaterial;
    @Getter private final String weaponMaterial;
    @Getter private final String colorCode;
    @Getter private final String itemDisplayName;
    @Getter private final int maxDurability;
}