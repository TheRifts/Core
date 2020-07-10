package me.Lozke.utils.ItemMenu.icons;

import me.Lozke.utils.ItemMenu.events.MenuClickEvent;
import me.Lozke.utils.ItemMenu.menus.ItemMenu;
import me.Lozke.utils.Items;
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

        if (!displayName.equals("")) {
            Items.formatItem(icon, displayName);
        }
        if (!(lore[0].equals("") && lore.length == 1)) {
            Items.setLore(icon, lore);
        }
    }

    public MenuIcon(ItemStack icon, String displayName) {
        this(icon, displayName, "");
    }

    public MenuIcon(ItemStack icon) {
        this(icon, "", "");
    }

    public MenuIcon () { }

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
        Items.formatItem(icon, displayName);
        this.displayName = displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(String... strings) {
        setLore(Arrays.asList(strings));
    }
    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void updateLore() {
        Items.setLore(icon, (String[]) lore.toArray());
    }

    public void updateIcon() {
        parentMenu.updateIcon(this);
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