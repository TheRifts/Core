package me.Lozke.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class CoreDamageListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        /*
        Entity damager;
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent)event;
            damager = edbeEvent.getDamager();

            event.setDamage(getDamage(damager));
        }

        Entity damaged = event.getEntity();
        if (damaged instanceof LivingEntity) {
            LivingEntity damagedLivingEntity = (LivingEntity)damaged;

            //Ensure health values remain valid
            if (isDeath(damagedLivingEntity, event.getDamage())) {
                event.setDamage(damagedLivingEntity.getHealth());
            }
        }
         */
    }


    /*
    private double getDamage(Entity damager) {
        ItemStack item = null;
        if (damager instanceof Player) {
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
    */

    private boolean isDeath(LivingEntity damaged, double damage) {
        return damage >= damaged.getHealth();
    }
}