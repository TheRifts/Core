package me.Lozke.utils.ItemMenu.menus;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuHolder implements InventoryHolder {

    private ItemMenu menu;

    public MenuHolder(ItemMenu menu) {
        this.menu = menu;
    }

    public ItemMenu getItemMenu() {
        return menu;
    }

    @Override
    public Inventory getInventory() {
        return menu.getInventory();
    }
}
