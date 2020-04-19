package me.Lozke.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.handlers.ItemHandler;
import me.Lozke.utils.Items;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArmorChangeListener implements Listener {

    private FallingAutism plugin;

    public ArmorChangeListener(FallingAutism plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onArmourChange(PlayerArmorChangeEvent event) {
        AutisticPlayer autisticPlayer;
        boolean oldItemIsReal = Items.isRealItem(event.getOldItem());
        boolean newItemIsReal = Items.isRealItem(event.getNewItem());
        if (oldItemIsReal || newItemIsReal) {
            autisticPlayer = plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
            if (oldItemIsReal) {
                ItemHandler.handleStats(autisticPlayer, event.getOldItem(), false);
            }
            if (newItemIsReal) {
                ItemHandler.handleStats(autisticPlayer, event.getNewItem(), true);
            }
        }
    }
}
