package me.Lozke.events;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.items.NamespacedKeys;
import me.Lozke.data.TimedPlayerStatus;
import me.Lozke.managers.PlayerManager;
import me.Lozke.utils.Items;
import me.Lozke.utils.Logger;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
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
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();

        event.setDamage(getDamage(damager));

        handleTimedStatuses(damager, damaged);
        handleTimedStatuses(damaged, damager);

        if(isPlayerDeath(damaged, event.getDamage())) {
            handlePlayerDeath((Player)damaged);
        }


    }

    private double getDamage(Entity damager) {
        if (damager instanceof  Player) {
            Player playerDamager = (Player)damager;
            ItemStack item = playerDamager.getInventory().getItemInMainHand();

            if (Items.isRealItem(item)) {
                PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
                if (dataContainer.has(NamespacedKeys.DMG, PersistentDataType.INTEGER)) {
                    return dataContainer.get(NamespacedKeys.DMG, PersistentDataType.INTEGER);
                }
            }
        }
        return 1;
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

    private boolean isPlayerDeath(Entity damaged, double damage) {
        if(damaged instanceof Player) {
            Player playerDamaged = (Player)damaged;
            return damage >= playerDamaged.getHealth();
        }
        return false;
    }

    private void handlePlayerDeath(Player killedPlayer) {
        UUID uniqueId = killedPlayer.getUniqueId();
        AutisticPlayer autisticPlayer = plugin.getPlayerManager().getPlayer(uniqueId);

        Logger.broadcast("PLAYER " + killedPlayer.getName() + " DIED LOL");

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
}
