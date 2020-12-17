package me.Lozke.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Rarity {
    COMMON("I", new ItemStack(Material.WHITE_CONCRETE), "&7"),
    UNCOMMON("II", new ItemStack(Material.LIME_CONCRETE), "&2"),
    RARE("III", new ItemStack(Material.LIGHT_BLUE_CONCRETE), "&b"),
    UNIQUE("IV", new ItemStack(Material.PURPLE_CONCRETE), "&5"),
    ANCIENT("V", new ItemStack(Material.YELLOW_CONCRETE), "&e");

    public static Rarity[] types = Rarity.values();

    private final String symbol;
    private final ItemStack icon;
    private final String colorCode;
    
    Rarity(String symbol, ItemStack icon, String colorCode) {
        this.symbol = symbol;
        this.icon = icon;
        this.colorCode = colorCode;
    }

    public String getSymbol() {
        return symbol;
    }
    public ItemStack getIcon() {
        return icon;
    }
    public String getColorCode() {
        return colorCode;
    }
}
