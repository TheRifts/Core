package me.Lozke.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemMenu {

    private Inventory inventory;
    private ItemMenu parent;
    private InventoryType type;
    private int size;
    private String title;
    private ItemStack[] items;

    public ItemMenu(InventoryType type, int size, String title, ItemStack... items) {
        this.type = type;
        this.size = size;
        this.title = title;
        this.items = items;
        if (type != null) {
            inventory = Bukkit.createInventory(null, type, title);
        }
        else {
            inventory = Bukkit.createInventory(null, size, title);
        }
        for (int slot = 0; slot < inventory.getSize() && slot < items.length; slot++) {
            inventory.setItem(slot, items[slot]);
        }
    }

    public ItemMenu(InventoryType type, String title, ItemStack... items) {
        this(type, 0, title, items);
    }

    public ItemMenu(int size, String title, ItemStack... items) {
        this(null, size, title, items);
    }

    public void setParent(ItemMenu parent) {
        this.parent = parent;
    }

    public ItemMenu getParent() {
        return parent;
    }

    public void openParent(Player player) {
        if (parent != null) {
            parent.openMenu(player);
        }
    }

    public void openMenu(Player player) {
        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void updateSlot(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }
}
