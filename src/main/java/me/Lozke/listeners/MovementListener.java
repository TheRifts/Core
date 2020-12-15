package me.Lozke.listeners;

import me.Lozke.events.LandEvent;
import me.Lozke.events.LaunchEvent;
import me.Lozke.utils.MoveUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;
import java.util.WeakHashMap;

public class MovementListener {

    private final Map<Player, Boolean> groundedLastTickMap = new WeakHashMap<>();

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getTo() == null) {
            return;
        }
        boolean grounded = ((Entity) event.getPlayer()).isOnGround();
        boolean wasOnGround = groundedLastTickMap.getOrDefault(event.getPlayer(), false);

        if (wasOnGround && !grounded) {
            LaunchEvent ev = new LaunchEvent(event.getPlayer(), event.getPlayer().getLocation());
            Bukkit.getPluginManager().callEvent(ev);
        } else if (!wasOnGround && grounded) {
            LandEvent ev = new LandEvent(event.getPlayer(), event.getPlayer().getLocation());
            Bukkit.getPluginManager().callEvent(ev);
        }

        if (grounded) {
            MoveUtil.setLastGrounded(event.getPlayer());
        }
        groundedLastTickMap.put(event.getPlayer(), grounded);

        if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
            MoveUtil.setLastMoved(event.getPlayer());
        }
    }
}
