package me.Lozke.events;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.items.NamespacedKeys;
import me.Lozke.data.TimedPlayerStatus;
import me.Lozke.managers.PlayerManager;
import me.Lozke.utils.Items;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
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

import java.util.ArrayList;
import java.util.List;
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
            return plugin.getPlayerManager().getPlayer(damager.getUniqueId()).hasEnergy();
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
        if (Items.isRealItem(item)) {
            PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
            if (dataContainer.has(NamespacedKeys.DMG, PersistentDataType.INTEGER)) {
                return dataContainer.get(NamespacedKeys.DMG, PersistentDataType.INTEGER);
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

        PlayerManager manager = FallingAutism.getPluginInstance().getPlayerManager();

        AutisticPlayer autisticPlayer = manager.getPlayer(player.getUniqueId());
        if (autisticPlayer == null) {
            autisticPlayer = manager.addPlayer(player.getUniqueId());
        }

        if (entity instanceof Monster) {
            autisticPlayer.setStatus(TimedPlayerStatus.MOB_COMBAT);
        }
        else if (entity instanceof Player) {
            autisticPlayer.setStatus(TimedPlayerStatus.PLAYER_COMBAT);
        }
    }

    private boolean isDeath(LivingEntity damaged, double damage) {
        return damage >= damaged.getHealth();
    }

    /*
    private void handlePlayerDeath(Player killedPlayer, Entity killer) {
        UUID uniqueId = killedPlayer.getUniqueId();
        AutisticPlayer autisticPlayer = plugin.getPlayerManager().getPlayer(uniqueId);

        //Send death message
        for (Entity nearbyEntity : killedPlayer.getWorld().getNearbyEntities(killedPlayer.getLocation(), 100, 100, 100)) {
            if (nearbyEntity instanceof Player) {
                nearbyEntity.sendMessage(killedPlayer.getName() + " was slain by " + killer.getName());
            }
        }
        killedPlayer.playSound(killedPlayer.getLocation(), Sound.BLOCK_ANVIL_LAND, (float)1.0, (float)0.75);

        //Teleport player
        killedPlayer.teleport(killedPlayer.getWorld().getSpawnLocation());

        //Heal to full health
        killedPlayer.setHealth(killedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        //Remove all statuses
        List<TimedPlayerStatus> statusesToRemove = new ArrayList<>(autisticPlayer.getStatuses());
        for(TimedPlayerStatus status : statusesToRemove) {
            autisticPlayer.removeStatus(status);
        }

        //Refill energy bar
        autisticPlayer.setEnergy(AutisticPlayer.fullEnergy);
    }
     */
}
