package me.Lozke.utils.ItemMenu.events;

import me.Lozke.utils.ItemMenu.menus.ItemMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuClickEvent extends InventoryClickEvent {

    private Player player;
    private ItemMenu menu;

    public MenuClickEvent(InventoryClickEvent event, ItemMenu menu) {
        super(event.getView(), event.getSlotType(), event.getRawSlot(), event.getClick(), event.getAction(), event.getHotbarButton());
        this.player = (Player) event.getWhoClicked();
        this.menu = menu;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemMenu getMenu() {
        return menu;
    }

    public void openParent() {
        menu.getParent().openMenu(player);
    }
}
