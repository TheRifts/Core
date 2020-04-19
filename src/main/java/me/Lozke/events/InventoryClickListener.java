package me.Lozke.events;

import me.Lozke.data.Tier;
import me.Lozke.handlers.ItemHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        ItemStack cursorItem = event.getCursor();
        ItemStack currentItem = event.getCurrentItem();
        Inventory inventory = event.getClickedInventory();

        if (inventory == null ||
                !event.getClickedInventory().getType().equals(InventoryType.PLAYER) ||
                !ItemHandler.isTiered(currentItem) ||
                !ItemHandler.isTiered(cursorItem)) {
            return;
        }

        Tier tierCursor = ItemHandler.getTier(cursorItem);
        Tier tierCurrent = ItemHandler.getTier(currentItem);
        if (tierCursor!= tierCurrent) {
            return;
        }

        if (cursorItem.getType().equals(Material.MAGMA_CREAM)) {
            event.setCancelled(true);
            ItemHandler.randomizeAttributes(currentItem);
        }
        else if (cursorItem.getType().equals(Material.BLAZE_POWDER)) {
            event.setCancelled(true);
            ItemHandler.randomizeStats(currentItem);
        }
        else {
            return;
        }
        cursorItem.setAmount(cursorItem.getAmount() - 1);
    }
}
