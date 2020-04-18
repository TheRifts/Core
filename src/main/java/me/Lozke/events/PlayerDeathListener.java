/*
 * Created by Noah Pritchard on 4/17/2020
 */
package me.Lozke.events;

import me.Lozke.FallingAutism;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private FallingAutism plugin;

    public PlayerDeathListener(FallingAutism plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.setDroppedExp(0);

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, (float)1.0, (float)0.75);
    }
}
