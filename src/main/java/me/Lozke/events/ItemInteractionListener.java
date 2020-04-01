package me.Lozke.events;

import me.Lozke.RetardRealms;
import me.Lozke.data.ItemData;
import me.Lozke.managers.MobManager;
import me.Lozke.utils.Text;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemInteractionListener implements Listener {

    private MobManager mobManager;

    public ItemInteractionListener() {
        mobManager = RetardRealms.getPluginInstance().getMobManager();
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        //Prevents event from firing twice, we only care if player is using main hand!
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        ItemStack handItem = player.getItemInHand();
        ItemMeta itemMeta = handItem.getItemMeta();
        if (handItem.getType() != Material.SHEARS || itemMeta == null || !itemMeta.getDisplayName().equals(Text.colorize("&eSpawner Wand"))) {
            return;
        }
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            mobManager.removeSpawner(event.getClickedBlock().getLocation());
            event.setCancelled(true);
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
            NamespacedKey key = ItemData.spawnerWandToggle;
            if (dataContainer.has(key, PersistentDataType.INTEGER)) {
                int value = dataContainer.get(key, PersistentDataType.INTEGER); //Let's convert this to a boolean DataType!
                if (value == 0) { //Placement Mode
                    placeSpawner(event.getClickedBlock().getLocation(), event.getBlockFace());
                }
                if (value == 1) { //Edit Mode
                    if (mobManager.isSpawner(event.getClickedBlock().getLocation())) {
                        player.openInventory(mobManager.openGUI(event.getClickedBlock().getLocation()));
                    }
                }
            }
        }
    }

    public void placeSpawner(Location location, BlockFace blockFace) {
        //TODO: Change these to only add to the effected coordinate. Less maths!
        switch (blockFace) {
            case NORTH:
                location.add(0, 0, -1);
                break;
            case EAST:
                location.add(1, 0, 0);
                break;
            case SOUTH:
                location.add(0, 0, 1);
                break;
            case WEST:
                location.add(-1, 0, 0);
                break;
            case UP:
                location.add(0, 1, 0);
                break;
            case DOWN:
                location.add(0, -1, 0);
                break;
        }
        RetardRealms.getPluginInstance().getMobManager().createSpawner(location);
    }
}
