package me.Lozke.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Rarity {
    COMMON("-", new ItemStack(Material.WHITE_CONCRETE), "&7"),
    UNCOMMON("=", new ItemStack(Material.LIME_CONCRETE), "&2"),
    RARE("≡", new ItemStack(Material.LIGHT_BLUE_CONCRETE), "&b"),
    UNIQUE("#", new ItemStack(Material.PURPLE_CONCRETE), "&5"),
    ANCIENT("+", new ItemStack(Material.YELLOW_CONCRETE), "&e");

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
