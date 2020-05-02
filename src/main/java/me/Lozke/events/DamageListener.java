package me.Lozke.events;

import me.Lozke.FallingAutism;
import me.Lozke.data.items.NamespacedKeys;
import me.Lozke.data.TimedPlayerStatus;
import me.Lozke.handlers.ItemHandler;
import me.Lozke.managers.PlayerManager;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class DamageListener implements Listener {

    private FallingAutism plugin;

    public DamageListener(FallingAutism plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        //Get those involved
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();

        //Make sure the damager is able to deal damage (energy)
        if(!canDamage(damager)) {
            event.setCancelled(true);
            return;
        }

        //Log the amount of damage to be dealt
        event.setDamage(getDamage(damager));

        //Deal with combat timers
        handleTimedStatuses(damager, damaged);
        handleTimedStatuses(damaged, damager);

        //Ensure health values remain valid (on death)
        if(damaged instanceof LivingEntity) {
            LivingEntity damagedLivingEntity = (LivingEntity)damaged;
            if(isDeath(damagedLivingEntity, event.getDamage())) {
                event.setDamage(damagedLivingEntity.getHealth());
            }

            //Deal Damage
            dealDamage((LivingEntity)damaged, event.getDamage());
            event.setCancelled(true);
        }

    }


    public boolean canDamage(Entity damager) {
        if (damager instanceof Player) {
            return plugin.getPlayerManager().hasEnergy(damager.getUniqueId());
        }
        return true;
    }

    private double getDamage(Entity damager) {
        ItemStack item = null;
        if (damager instanceof  Player) {
            Player playerDamager = (Player)damager;
            item = playerDamager.getInventory().getItemInMainHand();
        }
        if (damager instanceof Monster) {
            Monster monster = (Monster)damager;
            if (monster.getEquipment() != null) {
                item = monster.getEquipment().getItemInMainHand();
            }
        }
        if (ItemHandler.isRealItem(item)) {
            PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
            if (dataContainer.has(NamespacedKeys.damage, PersistentDataType.INTEGER)) {
                return dataContainer.get(NamespacedKeys.damage, PersistentDataType.INTEGER);
            }
        }

        return 1;
    }

    private void dealDamage(LivingEntity damaged, double damage) {
        damaged.setHealth(damaged.getHealth()-damage);
        damaged.playEffect(EntityEffect.HURT);
    }

    private void handleTimedStatuses(Entity player, Entity entity) {
        if (!(player instanceof Player)) {
            return;
        }

        UUID uuid = player.getUniqueId();
        PlayerManager manager = plugin.getPlayerManager();

        if(!manager.isAutisticPlayer(uuid)) {
            return;
        }

        if (entity instanceof Monster) {
            manager.updateStatus(uuid, TimedPlayerStatus.MOB_COMBAT, true);
        }
        else if (entity instanceof Player) {
            manager.updateStatus(uuid, TimedPlayerStatus.PLAYER_COMBAT, true);
        }
    }

    private boolean isDeath(LivingEntity damaged, double damage) {
        return damage >= damaged.getHealth();
    }
}
