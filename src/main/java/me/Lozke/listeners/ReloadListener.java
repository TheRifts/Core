package me.Lozke.listeners;

import me.Lozke.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ReloadListener implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public static void onReload(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equalsIgnoreCase("/reload confirm")) {
            Bukkit.broadcastMessage(Text.colorize(
                    "&a&lAttention&a&l!&a " +
                    "A hotfix is currently being applied to the server by an admin. " +
                    "This task will only take a few moments to process, hang tight!"));
        }
    }

}
