package me.Lozke.handlers;

import me.Lozke.RiftsCore;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BossBarHandler extends BukkitRunnable {

    private Map<UUID, BossBar> activeBars = new ConcurrentHashMap<>();

    public BossBarHandler(RiftsCore plugin) {
        runTaskTimer(plugin, 0L , 1L);
        Bukkit.getOnlinePlayers().forEach(this::createBar);
    }

    public void createBar(Player player) {
        createBar(player.getUniqueId());
    }
    public void createBar(UUID uuid) {
        if (hasBar(uuid)) {
            return;
        }
        BossBar bossbar = Bukkit.createBossBar(
                "",
                BarColor.RED,
                BarStyle.SOLID);
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return;
        }
        activeBars.put(uuid, bossbar);
        updateBar(uuid);
        bossbar.addPlayer(player);
    }

    public void updateBar(Player player) {
        updateBar(player.getUniqueId());
    }
    public void updateBar(UUID uuid) {
        if (!hasBar(uuid)) {
            createBar(uuid);
            return;
        }
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            removeBar(uuid);
            return;
        }
        int maxHealth = (int) player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        int health = (int) player.getHealth();
        BossBar bossbar = activeBars.get(uuid);
        bossbar.setTitle("HP " + health + "/" + maxHealth);
        double progress = (double) health/maxHealth;
        if (progress > 1.0) {
            player.setHealth(maxHealth);
            progress = 1.0;
        }
        bossbar.setProgress(progress);
    }
    public void updateAll() {
        activeBars.keySet().forEach(this::updateBar);
    }

    public void removeBar(Player player) {
        removeBar(player.getUniqueId());
    }
    public void removeBar(UUID uuid) {
        if (!hasBar(uuid)) {
            return;
        }
        activeBars.get(uuid).removeAll();
        activeBars.remove(uuid);
    }
    public void removeAll() {
        activeBars.keySet().forEach(this::removeBar);
    }

    public boolean hasBar(Player player) {
        return hasBar(player.getUniqueId());
    }
    public boolean hasBar(UUID uuid) {
        return activeBars.containsKey(uuid);
    }

    @Override
    public void run() {
        updateAll();
    }
}
