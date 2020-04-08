package me.Lozke.events;

import me.Lozke.data.ItemData;
import me.Lozke.utils.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SpawnerWandToggleListener implements Listener {

    @EventHandler
    public void onHandSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack handItem = player.getItemInHand();
        if (handItem.getType() != Material.SHEARS || !handItem.getItemMeta().getDisplayName().equals(Text.colorize("&eSpawner Wand"))) {
            return;
        }
        ItemMeta itemMeta = handItem.getItemMeta();
        if (itemMeta != null) {
            PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
            NamespacedKey key = ItemData.spawnerWandToggle;
            if (dataContainer.has(key, PersistentDataType.INTEGER)) {
                int value = dataContainer.get(key, PersistentDataType.INTEGER);
                if (value == 0) {
                    dataContainer.set(key, PersistentDataType.INTEGER, 1);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&eEdit Mode Activated")));
                }
                if (value == 1) {
                    dataContainer.set(key, PersistentDataType.INTEGER, 0);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&ePlacement Mode Activated")));
                }
            }
            handItem.setItemMeta(itemMeta);
            event.setCancelled(true);
        }
    }
}
