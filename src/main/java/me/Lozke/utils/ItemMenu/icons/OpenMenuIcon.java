package me.Lozke.utils.ItemMenu.icons;

import me.Lozke.utils.ItemMenu.events.MenuClickEvent;
import me.Lozke.utils.ItemMenu.menus.ItemMenu;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class OpenMenuIcon extends MenuIcon {

    private ItemMenu defaultMenu;
    private HashMap<ClickType, ItemMenu> actions;

    public OpenMenuIcon(ItemStack icon, ItemMenu menu) {
        super(icon);
        this.defaultMenu = menu;
        this.actions = new HashMap<>();
        actions.put(ClickType.LEFT, menu);
    }

    public OpenMenuIcon(ItemStack icon) {
        super(icon);
        this.actions = new HashMap<>();
    }

    @Override
    public void onItemClick(MenuClickEvent event) {
        ItemMenu openingMenu;
        if (actions.get(event.getClick()) == null) {
            openingMenu = defaultMenu;
        }
        else {
            openingMenu = actions.get(event.getClick());
        }
        openingMenu.updateMenu();
        openingMenu.openMenu(event.getPlayer());
    }

    public OpenMenuIcon addClickAction(ClickType clickType, ItemMenu menu) {
        actions.put(clickType, menu);
        return this;
    }
}