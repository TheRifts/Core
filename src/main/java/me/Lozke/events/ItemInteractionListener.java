package me.Lozke.events;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.items.NamespacedKeys;
import me.Lozke.handlers.ItemHandler;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemInteractionListener implements Listener {
    private MobManager mobManager;
    private List<UUID> ignoredPlayers;

    private FallingAutism plugin;


    public ItemInteractionListener(FallingAutism plugin) {
        mobManager = FallingAutism.getPluginInstance().getMobManager();
        ignoredPlayers = new ArrayList<>();

        this.plugin = plugin;
    }

    @EventHandler
    public void onInteraction(PlayerInteractEvent event) {
        //Prevents event from firing twice, we only care if player is using main hand!
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        handleSpawnerWand(event);
        handleEnergy(event);
    }

    private void handleSpawnerWand(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        //Prevent event from firing twice if spawner is at edge of melee range
        if(ignoredPlayers.contains(uniqueId)) {
            return;
        }
        ignoredPlayers.add(uniqueId);
        new BukkitRunnable() {
            @Override
            public void run() {
                ignoredPlayers.remove(uniqueId);
            }
        }.runTaskLaterAsynchronously(FallingAutism.getPluginInstance(), 1);


        //TODO: Check player's Rank and if too low return here.

        ItemStack handItem = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = handItem.getItemMeta();
        if (handItem.getType() != Material.SHEARS || itemMeta == null || !itemMeta.getDisplayName().equals(Text.colorize("&eSpawner Wand"))) {
            return;
        }

        Action action = event.getAction();
        if(action == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true); //Prevent destroying blocks with spawner wand
        }

        Location location;
        try {
            if (action == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                location = event.getClickedBlock().getLocation();
            } else {
                location = player.getTargetBlockExact(50).getLocation(); //We should consider calculating the actual maximum range instead of capping it at 50
            }
        } catch (NullPointerException ignore) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&cOut Of Range")));
            return;
        }

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey key = NamespacedKeys.spawnerWandToggle;
        if (dataContainer.has(key, PersistentDataType.INTEGER)) {
            int value = dataContainer.get(key, PersistentDataType.INTEGER); //Let's convert this to a boolean DataType!
            if (value == 0) { //Placement Mode
                switch(event.getAction()) {
                    case LEFT_CLICK_BLOCK:
                    case LEFT_CLICK_AIR:
                        if(mobManager.isSpawner(location)) {
                            mobManager.removeSpawner(location);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&aSpawner Removed")));
                        }
                        else {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&cNo Spawner Found")));
                        }
                        break;
                    case RIGHT_CLICK_BLOCK:
                        placeSpawner(location, event.getBlockFace());
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&aSpawner Placed")));
                        break;
                    case RIGHT_CLICK_AIR:
                        List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 50);
                        if(lastTwoTargetBlocks.size()!=2) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&cPlacement Failure")));
                            return;
                        }
                        BlockFace blockFace = lastTwoTargetBlocks.get(1).getFace(lastTwoTargetBlocks.get(0));
                        if(blockFace==null) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&cPlacement Failure")));
                            return;
                        }
                        placeSpawner(location, blockFace);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&aSpawner Placed")));
                        break;
                }
            }
            if (value == 1) { //Edit Mode
                switch(event.getAction()) {
                    case LEFT_CLICK_BLOCK:
                    case LEFT_CLICK_AIR:
                        if (mobManager.isSpawner(location)) {
                            player.openInventory(mobManager.openGUI(location));
                        }
                        else {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&cNo Spawner Found")));
                        }
                        break;
                    case RIGHT_CLICK_BLOCK:
                    case RIGHT_CLICK_AIR:
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Text.colorize("&cFixed Spawns Not Implemented")));
                        //TODO placing fixed spawn locations
                        break;
                }
            }
        }
    }

    private void placeSpawner(Location location, BlockFace blockFace) {
        //TODO: Change these to only add to the affected coordinate. Less maths!
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

    private void handleEnergy(PlayerInteractEvent event) {
        Action action = event.getAction();

        if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            AutisticPlayer autisticPlayer = plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
            ItemStack item = event.getItem();
            float energy = autisticPlayer.getEnergy();
            if(energy > 0) {
                autisticPlayer.setEnergy(energy- ItemHandler.getItemEnergyCost(item));
            }
        }
    }
}
