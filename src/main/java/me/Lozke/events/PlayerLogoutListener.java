package me.Lozke.events;

import me.Lozke.FallingAutism;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLogoutListener implements Listener {

    FallingAutism plugin;


    public PlayerLogoutListener(FallingAutism plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        handleEssentialLogout(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        handleEssentialLogout(event.getPlayer());
    }


    private void handleEssentialLogout(Player player) {
        UUID uuid = player.getUniqueId();

        plugin.getBossBarHandler().removeBar(uuid);
        plugin.getPlayerManager().savePlayer(uuid);
        plugin.getPlayerManager().removePlayer(uuid);
    }
}
