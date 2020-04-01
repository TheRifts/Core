package me.Lozke.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import me.Lozke.data.ItemData;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ArmourChangeListener implements Listener {

    @EventHandler
    public void onArmourChange(PlayerArmorChangeEvent event) {
        if (event.getOldItem().getItemMeta() != null) {
            handleStats(event.getPlayer(), event.getOldItem(), false);
        }
        if (event.getNewItem().getItemMeta() != null) {
            handleStats(event.getPlayer(), event.getNewItem(), true);
        }
    }

    //TODO: Move this into item class, once it is created.
    private void handleStats(Player player, ItemStack item, boolean equipped) {
        PersistentDataContainer dataContainer = item.getItemMeta().getPersistentDataContainer();
        if (dataContainer.has(ItemData.realItem, PersistentDataType.STRING)) {
            AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            int itemHP = dataContainer.get(ItemData.HP, PersistentDataType.INTEGER);
            if (equipped) {
                maxHealth.setBaseValue((int) maxHealth.getValue() + itemHP);
            }
            else {
                maxHealth.setBaseValue((int) maxHealth.getValue() - itemHP);
                if (player.getHealth() > maxHealth.getValue()) {
                    try {
                        player.setHealth(player.getHealth() - itemHP);
                    } catch (IllegalArgumentException exception) {
                        player.setHealth(maxHealth.getValue());
                    }
                }
            }
        }
    }
}
