package me.Lozke.listeners;

import me.Lozke.AgorianRifts;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class DamagedEntityListener implements Listener {

    private Plugin plugin;

    public DamagedEntityListener(AgorianRifts plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            event.setCancelled(true);
            return;
        }

        LivingEntity entity = (LivingEntity) event.getEntity();
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            entity.setNoDamageTicks(0);
            entity.setMaximumNoDamageTicks(0);
        }, 0L);
    }

}
