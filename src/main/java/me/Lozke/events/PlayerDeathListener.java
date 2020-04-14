/*
 * Created by Noah Pritchard on 4/13/2020
 */
package me.Lozke.events;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.TimedPlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getScheduler;

public class PlayerDeathListener implements Listener {
    private FallingAutism plugin;

    public PlayerDeathListener(FallingAutism plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID uniqueId = player.getUniqueId();

        //Remove all statuses on death
        AutisticPlayer autisticPlayer = plugin.getPlayerManager().getPlayer(uniqueId);
        List<TimedPlayerStatus> statusesToRemove = new ArrayList<>(autisticPlayer.getStatuses());
        for(TimedPlayerStatus status : statusesToRemove) {
            autisticPlayer.removeStatus(status);
        }

        //Force respawn
        getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
            public void run() {
                player.spigot().respawn();
            }
        }, 1L);
    }
}
