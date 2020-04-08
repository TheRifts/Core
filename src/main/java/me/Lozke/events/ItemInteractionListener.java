package me.Lozke.events;

import me.Lozke.RetardRealms;
import me.Lozke.data.items.NamespacedKeys;
import me.Lozke.managers.MobManager;
import me.Lozke.utils.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
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

import java.util.List;

public class ItemInteractionListener implements Listener {

    private MobManager mobManager;

    public ItemInteractionListener() {
        mobManager = RetardRealms.getPluginInstance().getMobManager();
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        //TODO: Check player's Rank and if too low return here.

        //Prevents event from firing twice, we only care if player is using main hand!
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        ItemStack handItem = player.getItemInHand();
        ItemMeta itemMeta = handItem.getItemMeta();
        if (handItem.getType() != Material.SHEARS || itemMeta == null || !itemMeta.getDisplayName().equals(Text.colorize("&eSpawner Wand"))) {
            return;
        }

        Location location;
        try {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                location = event.getClickedBlock().getLocation();
            } else {
                location = player.getTargetBlockExact(50).getLocation(); //We should consider calculating the actual maximum range instead of capping it at 50
            }
        } catch (NullPointerException ignore) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&cOut Of Range")));
            return;
        }

        switch (event.getAction()) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                if (mobManager.isSpawner(location)) {
                    event.setCancelled(true); //Prevent destroying blocks manually
                }
                mobManager.removeSpawner(location);
                break;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
                NamespacedKey key = NamespacedKeys.spawnerWandToggle;
                if (dataContainer.has(key, PersistentDataType.INTEGER)) {
                    int value = dataContainer.get(key, PersistentDataType.INTEGER); //Let's convert this to a boolean DataType!
                    if (value == 0) { //Placement Mode
                        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            placeSpawner(location, event.getBlockFace());
                        } else {
                            List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 50);
                            if (lastTwoTargetBlocks.size() != 2) {
                                return;
                            }
                            BlockFace blockFace = lastTwoTargetBlocks.get(1).getFace(lastTwoTargetBlocks.get(0));
                            if (blockFace == null) {
                                return;
                            }
                            placeSpawner(location, blockFace);
                        }
                    }
                    if (value == 1) { //Edit Mode
                        if (mobManager.isSpawner(location)) {
                            player.openInventory(mobManager.openGUI(location));
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
        mobManager.createSpawner(location);
    }
}
