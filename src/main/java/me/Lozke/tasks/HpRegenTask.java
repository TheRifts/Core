/*
 * Created by Noah Pritchard on 4/14/2020
 */
package me.Lozke.tasks;

import me.Lozke.FallingAutism;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class HpRegenTask extends BukkitRunnable {
    private static long baseHPRegen = 5;

    private UUID uniqueId;

    public HpRegenTask(UUID uniqueId) {
        this.uniqueId = uniqueId;
        runTaskTimerAsynchronously(FallingAutism.getPluginInstance(), 0L, 20L);
    }

    @Override
    public void run() {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player != null) {
            double health = player.getHealth();
            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            if(health != maxHealth) {
                health+= baseHPRegen;
                if(health > maxHealth) {
                    health = maxHealth;
                }
            }
            player.setHealth(health);
        }
    }
}
