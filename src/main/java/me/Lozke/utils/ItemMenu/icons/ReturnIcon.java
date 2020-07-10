package me.Lozke.utils.ItemMenu.icons;

import me.Lozke.utils.ItemMenu.events.MenuClickEvent;
import me.Lozke.utils.ItemMenu.menus.ItemMenu;
import org.bukkit.inventory.ItemStack;

public class ReturnIcon extends MenuIcon {

    public ReturnIcon(ItemStack icon) {
        super(icon);
    }

    @Override
    public void onItemClick(MenuClickEvent event) {
        ItemMenu parent = event.getMenu().getParent();
        if (parent != null) {
            parent.updateMenu();
            parent.openMenu(event.getPlayer());
        }
    }
}
