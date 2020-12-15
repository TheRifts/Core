package me.Lozke.utils;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class MoveUtil {

    private final static Map<Player, Long> LAST_MOVED = new WeakHashMap<>();
    private final static Map<Player, Long> LAST_GROUNDED = new WeakHashMap<>();
    private final static Map<Player, Long> SNEAK_START = new WeakHashMap<>();

    public static void setLastMoved(Player player) {
        LAST_MOVED.put(player, System.currentTimeMillis());
    }

    public static void setLastGrounded(Player player) {
        LAST_GROUNDED.put(player, System.currentTimeMillis());
    }

    public static boolean hasMoved(Player player) {
        return System.currentTimeMillis() - LAST_MOVED.getOrDefault(player, 0L) < 100;
    }

    public static long timeOffGround(Player player) {
        return System.currentTimeMillis() - LAST_GROUNDED.getOrDefault(player, 1L);
    }

    public static void setSneak(Player player) {
        if (player.isSneaking()) {
            if (!SNEAK_START.containsKey(player)) {
                SNEAK_START.put(player, System.currentTimeMillis());
            }
        } else {
            SNEAK_START.remove(player);
        }
    }

    @Deprecated
    public static int getLastSneak(UUID uuid) {
        for (Player p : SNEAK_START.keySet()) {
            if (p.getUniqueId().equals(uuid)) {
                return Math.toIntExact(System.currentTimeMillis() - SNEAK_START.get(p));
            }
        }
        return -1;
    }

    public static int getLastSneak(Player player) {
        if (SNEAK_START.containsKey(player)) {
            return Math.toIntExact(System.currentTimeMillis() - SNEAK_START.get(player));
        }
        return -1;
    }
}
