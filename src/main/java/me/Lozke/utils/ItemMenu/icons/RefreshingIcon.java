package me.Lozke.utils.ItemMenu.icons;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class RefreshingIcon extends MenuIcon {

    public RefreshingIcon(ItemStack icon) {
        super(icon);
    }

    public void timer(Plugin plugin) {

    }
    /*
    @Override
    public void onItemClick(MenuClickEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (event.getMenu().getInventory().getViewers().size() == 0) {
                    cancel();
                    return;
                }
                updateIcon();
            }
        }.runTaskTimer(this.plugin, 0, 20);
    }
     */
}
