/*
 * Created by Noah Pritchard on 4/17/2020
 */
package me.Lozke.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.setDroppedExp(0);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0F, 0.75F);
    }
}
