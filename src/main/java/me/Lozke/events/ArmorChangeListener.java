package me.Lozke.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.Lozke.handlers.ItemHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArmorChangeListener implements Listener {

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {
        boolean oldItemIsReal = ItemHandler.isRealItem(event.getOldItem());
        boolean newItemIsReal = ItemHandler.isRealItem(event.getNewItem());
        if (oldItemIsReal || newItemIsReal) {
            Player player = event.getPlayer();
            if (oldItemIsReal) {
                ItemHandler.handleStats(player, event.getOldItem(), false);
            }
            if (newItemIsReal) {
                ItemHandler.handleStats(player, event.getNewItem(), true);
            }
        }
    }
}
