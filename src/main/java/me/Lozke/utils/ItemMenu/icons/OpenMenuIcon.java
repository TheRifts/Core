package me.Lozke.utils.ItemMenu.icons;

import me.Lozke.utils.ItemMenu.events.MenuClickEvent;
import me.Lozke.utils.ItemMenu.menus.ItemMenu;
import org.bukkit.inventory.ItemStack;

public class OpenMenuIcon extends MenuIcon {

    private ItemMenu menu;

    public OpenMenuIcon(ItemStack icon, ItemMenu menu) {
        super(icon);
        this.menu = menu;
    }

    @Override
    public void onItemClick(MenuClickEvent event) {
        menu.openMenu(event.getPlayer());
    }
}
