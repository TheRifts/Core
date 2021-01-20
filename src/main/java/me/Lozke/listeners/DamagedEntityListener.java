package me.Lozke.listeners;

import me.Lozke.RiftsCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.WeakHashMap;

public class DamagedEntityListener implements Listener {

    private Plugin plugin;
    private final Map<LivingEntity, Long> MONSTER_HIT_COOLDOWN = new WeakHashMap<>();

    public DamagedEntityListener(RiftsCore plugin) {
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

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof LivingEntity)) return;
        if (!(event.getDamager() instanceof Player) && !canMonsterHit((LivingEntity) event.getDamager())) {
            event.setCancelled(true);
            return;
        }
        putMonsterHit((LivingEntity) event.getDamager());
    }

    private boolean canMonsterHit(LivingEntity entity) {
        return System.currentTimeMillis() > MONSTER_HIT_COOLDOWN.getOrDefault(entity, 0L);
    }

    public void putMonsterHit(LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player)) {
            MONSTER_HIT_COOLDOWN.put(livingEntity, System.currentTimeMillis() + 400);
        }
    }
}
