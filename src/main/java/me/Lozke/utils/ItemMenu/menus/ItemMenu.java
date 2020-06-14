package me.Lozke.utils.ItemMenu.menus;

import me.Lozke.utils.ItemMenu.events.MenuClickEvent;
import me.Lozke.utils.ItemMenu.icons.MenuIcon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemMenu {
    private Inventory inventory;
    private ItemMenu parent;
    private InventoryType type;
    private int size;
    private String title;
    private MenuIcon[] icons;

    public ItemMenu(InventoryType type, int size, String title, MenuIcon... icons) {
        this.type = type;
        this.size = size;
        this.title = title;

        if (type != null && type != InventoryType.CHEST) {
            inventory = Bukkit.createInventory(new MenuHolder(this), type, title);
        }
        else {
            inventory = Bukkit.createInventory(new MenuHolder(this), size * 9, title);
        }

        this.icons = new MenuIcon[inventory.getSize()];
        addIcons(icons);
    }

    public ItemMenu(InventoryType type, String title, MenuIcon... icons) {
        this(type, 0, title, icons);
    }

    public ItemMenu(InventoryType type, int size, MenuIcon... icons) {
        this(type, size, "", icons);
    }

    public ItemMenu(int size, String title, MenuIcon... icons) {
        this(null, size, title, icons);
    }

    public MenuIcon[] convertItemsToIcons(ItemStack... items) {
        MenuIcon[] returnVal = new MenuIcon[items.length];
        for (int i = 0; i < items.length; i++) {
            returnVal[i] = new MenuIcon(items[i]);
        }
        return returnVal;
    }

    public void addIcons(MenuIcon... mergingIcons) {
        for (int i = 0, j = 0; i < icons.length && j < mergingIcons.length; i++) {
            if (icons[i] == null) {
                setDisplayItem(i, mergingIcons[j]);
                j++;
            }
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void openMenu(Player player) {
        player.openInventory(inventory);
    }

    public ItemMenu getParent() {
        return parent;
    }

    public void setParent(ItemMenu parent) {
        this.parent = parent;
    }

    public void openParent(Player player) {
        if (parent != null) {
            parent.openMenu(player);
        }
    }

    public void updateSlot(int slot, ItemStack itemStack) {
        updateSlot(slot, new MenuIcon(itemStack));
    }
    public void updateSlot(int slot, MenuIcon icon) {
        inventory.setItem(slot, icon.getIcon());
        icons[slot] = icon;
    }

    public void setDisplayItem(int slot, ItemStack itemStack) {
        setDisplayItem(slot, new MenuIcon(itemStack));
    }
    public void setDisplayItem(int slot, MenuIcon icon) {
        inventory.setItem(slot, icon.getIcon());
        icons[slot] = icon;
    }

    public void addDisplayItem(ItemStack itemStack) {
        addDisplayItem(new MenuIcon(itemStack));
    }
    public void addDisplayItem(MenuIcon icon) {
        int slot = inventory.firstEmpty();
        setDisplayItem(slot, icon);
    }

    public void displayItems() {
        if (icons != null) {
            for (int slot = 0; slot < inventory.getSize() && slot < icons.length; slot++) {
                if (icons[slot] != null) {
                    inventory.setItem(slot, icons[slot].getIcon());
                }
            }
        }
    }

    public void clearItems() {
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            inventory.setItem(slot, null);
        }
    }

    public void handleClickEvent(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        if (slot >= 0 && slot <= icons.length && icons[slot] != null && event.getWhoClicked() instanceof Player) {
            icons[slot].onItemClick(new MenuClickEvent((Player) event.getWhoClicked(), this));
        }
    }
}