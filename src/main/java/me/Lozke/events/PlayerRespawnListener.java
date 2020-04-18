/*
 * Created by Noah Pritchard on 4/17/2020
 */
package me.Lozke.events;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.TimedPlayerStatus;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class PlayerRespawnListener implements Listener {

    private FallingAutism plugin;

    public PlayerRespawnListener(FallingAutism plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        AutisticPlayer autisticPlayer = plugin.getPlayerManager().getPlayer(player.getUniqueId());

        //Heal to full health
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        //Remove all statuses
        List<TimedPlayerStatus> statusesToRemove = new ArrayList<>(autisticPlayer.getStatuses());
        for(TimedPlayerStatus status : statusesToRemove) {
            autisticPlayer.removeStatus(status);
        }

        //Refill energy bar
        getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                autisticPlayer.setEnergy(AutisticPlayer.fullEnergy);
            }
        }, 1);
    }
}
