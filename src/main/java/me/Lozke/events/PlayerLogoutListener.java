package me.Lozke.events;

import me.Lozke.RetardRealms;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLogoutListener implements Listener {

    RetardRealms plugin;

    public PlayerLogoutListener(RetardRealms plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        removeBossbar(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        removeBossbar(event.getPlayer());
    }

    private void removeBossbar(Player player) {
        plugin.getBossBarHandler().removeBar(player);
    }
}
