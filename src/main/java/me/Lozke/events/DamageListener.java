package me.Lozke.events;

import me.Lozke.data.items.NamespacedKeys;
import org.bukkit.EntityEffect;
import org.bukkit.entity.LivingEntity;
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

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        ItemStack item = player.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        if (dataContainer.has(NamespacedKeys.realItem, PersistentDataType.STRING)) {
            event.setCancelled(true);
            LivingEntity entity = (LivingEntity) event.getEntity();
            double newHP = entity.getHealth() - dataContainer.get(NamespacedKeys.DMG, PersistentDataType.INTEGER);
            if (newHP > 0) {
                entity.setHealth(newHP);
                entity.playEffect(EntityEffect.HURT);
            }
            else {
                entity.setHealth(1);
                ((LivingEntity) event.getEntity()).damage(1, player);
            }
        }
    }
}
