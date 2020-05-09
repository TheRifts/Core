package me.Lozke.events;

import me.Lozke.data.items.NamespacedKeys;
import me.Lozke.handlers.ItemHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DamageListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        event.setDamage(getDamage(damager));
    }


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
}
