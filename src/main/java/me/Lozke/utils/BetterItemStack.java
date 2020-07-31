package me.Lozke.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BetterItemStack extends ItemStack {

    private NamespacedKeyWrapper namespacedKeyWrapper;

    public BetterItemStack(ItemStack stack) {
        this.namespacedKeyWrapper = new NamespacedKeyWrapper(stack);
    }

    public BetterItemStack(Material type) {
        this(new ItemStack(type));
    }

    public BetterItemStack(Material type, int amount) {
        this(new ItemStack(type, amount));
    }

    public NamespacedKeyWrapper getNamespacedKeyAPI() {
        return namespacedKeyWrapper;
    }
}