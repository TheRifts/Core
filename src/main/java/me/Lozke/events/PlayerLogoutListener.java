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
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        removeBossbar(player);
        savePlayer(uniqueId);
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        removeBossbar(player);
        savePlayer(uniqueId);
    }

    private void removeBossbar(Player player) {
        plugin.getBossBarHandler().removeBar(player);
    }

    private void savePlayer(UUID uniqueId) {
        plugin.getPlayerManager().savePlayer(uniqueId);
    }
}
