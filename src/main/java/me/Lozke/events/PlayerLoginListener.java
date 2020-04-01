package me.Lozke.events;

import me.Lozke.RetardRealms;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLoginListener implements Listener {

    private RetardRealms plugin;

    public PlayerLoginListener(RetardRealms plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setHealthScale(20.0);
        plugin.getBossBarHandler().createBar(player);
    }
}
