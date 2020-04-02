package me.Lozke.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.Lozke.handlers.ItemHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArmourChangeListener implements Listener {

    @EventHandler
    public void onArmourChange(PlayerArmorChangeEvent event) {
        if (event.getOldItem().getItemMeta() != null) {
            ItemHandler.handleStats(event.getPlayer(), event.getOldItem(), false);
        }
        if (event.getNewItem().getItemMeta() != null) {
            ItemHandler.handleStats(event.getPlayer(), event.getNewItem(), true);
        }
    }
}
