package me.Lozke.utils.ItemMenu.icons;

import me.Lozke.utils.ItemMenu.events.MenuClickEvent;
import me.Lozke.utils.ItemMenu.menus.ItemMenu;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MenuIcon {

    private ItemStack icon;
    private String displayName;
    private List<String> lore;

    private ItemMenu parentMenu;

    public MenuIcon(ItemStack icon, String displayName, String... lore) {
        this.icon = icon;
        this.displayName = displayName;
        this.lore = Arrays.asList(lore);
    }

    public MenuIcon(ItemStack icon, String displayName) {
        this(icon, displayName, "");
    }

    public MenuIcon(ItemStack icon) {
        this(icon, "", "");
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public MenuIcon setParent(ItemMenu menu) {
        this.parentMenu = menu;
        return this;
    }

    public ItemMenu getParent() {
        return parentMenu;
    }

    public void onItemClick(MenuClickEvent event) {

    }
}