/*
 * Created by Noah Pritchard on 4/17/2020
 */
package me.Lozke.events;

import me.Lozke.FallingAutism;
import me.Lozke.data.TimedPlayerStatus;
import me.Lozke.managers.PlayerManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashSet;

import static org.bukkit.Bukkit.getServer;

public class PlayerRespawnListener implements Listener {

    private FallingAutism plugin;


    public PlayerRespawnListener(FallingAutism plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerManager manager = plugin.getPlayerManager();

        //Heal to full health
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        //Remove all statuses
        HashSet<TimedPlayerStatus> statusesToRemove = new HashSet<>(manager.getStatuses(player));
        for(TimedPlayerStatus status : statusesToRemove) {
            manager.updateStatus(player, status, false);
        }

        //Refill energy bar
        getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                manager.updateEnergy(player, PlayerManager.fullEnergy);
            }
        }, 1);
    }
}
