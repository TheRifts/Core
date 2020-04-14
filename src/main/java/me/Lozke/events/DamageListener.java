package me.Lozke.events;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.items.NamespacedKeys;
import me.Lozke.data.TimedPlayerStatus;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DamageListener implements Listener {

    //TODO: REWRITE THIS ENTIRE SHIT SHOW

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof  Player) {
            Player playerDamager = (Player)event.getDamager();
            ItemStack item = playerDamager.getInventory().getItemInMainHand();
            if (item.hasItemMeta()) {
                ItemMeta itemMeta = item.getItemMeta();
                PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
                if (dataContainer.has(NamespacedKeys.realItem, PersistentDataType.STRING)) {
                    event.setCancelled(true);
                    LivingEntity entity = (LivingEntity)event.getEntity();
                    double newHP = entity.getHealth() - dataContainer.get(NamespacedKeys.DMG, PersistentDataType.INTEGER);
                    if (newHP>0) {
                        entity.setHealth(newHP);
                        entity.playEffect(EntityEffect.HURT);
                    }
                    else {
                        entity.setHealth(1);
                        ((LivingEntity)event.getEntity()).damage(1, playerDamager);
                    }
                }
            }
        }
        handleTimedStatuses(event.getDamager(), event.getEntity());
        handleTimedStatuses(event.getEntity(), event.getDamager());
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

}
