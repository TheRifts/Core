package me.Lozke.utils.ItemMenu.events;

import me.Lozke.utils.ItemMenu.menus.ItemMenu;
import org.bukkit.entity.Player;

public class MenuClickEvent {

    private Player player;
    private ItemMenu menu;

    private boolean goBack = false;
    private boolean close = false;
    private boolean update = false;

    public MenuClickEvent(Player player, ItemMenu menu) {
        this.player = player;
        this.menu = menu;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemMenu getMenu() {
        return menu;
    }

    public boolean willGoBack() {
        return goBack;
    }
    public void setWillGoBack(boolean goBack) {
        this.goBack = goBack;
        if (goBack) {
            close = false;
            update = false;
        }
    }

    public boolean willClose() {
        return close;
    }
    public void setWillClose(boolean close) {
        this.close = close;
        if (close) {
            goBack = false;
            update = false;
        }
    }

    public boolean willUpdate() {
        return update;
    }
    public void setWillUpdate(boolean update) {
        this.update = update;
        if (update) {
            goBack = false;
            close = false;
        }
    }
}
