package me.Lozke.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum Rarity {
    COMMON("I", new ItemStack(Material.WHITE_CONCRETE), "&7"),
    UNCOMMON("II", new ItemStack(Material.LIME_CONCRETE), "&2"),
    RARE("III", new ItemStack(Material.LIGHT_BLUE_CONCRETE), "&b"),
    UNIQUE("IV", new ItemStack(Material.PURPLE_CONCRETE), "&5"),
    ANCIENT("V", new ItemStack(Material.YELLOW_CONCRETE), "&e");

    public static Rarity[] types = Rarity.values();

    @Getter private final String symbol;
    @Getter private final ItemStack icon;
    @Getter private final String colorCode;
}
