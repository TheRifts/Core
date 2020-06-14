package me.Lozke.utils.ItemMenu.icons;

import me.Lozke.utils.ItemMenu.events.MenuClickEvent;
import org.bukkit.inventory.ItemStack;

public class CloseIcon extends MenuIcon {

    public CloseIcon(ItemStack icon) {
        super(icon);
    }

    @Override
    public void onItemClick(MenuClickEvent event) {
        event.getPlayer().closeInventory();
    }
}
