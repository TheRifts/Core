package me.Lozke.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LaunchEvent extends BaseEvent {

    private final Player player;
    private final Location location;

    public LaunchEvent(Player player, Location location) {
        this.player = player;
        this.location = location;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }
}
