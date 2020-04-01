package me.Lozke.commands;

import me.Lozke.data.ItemData;
import me.Lozke.utils.Text;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SpawnerWand extends Command {

    public SpawnerWand() {
        super("gibwand");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player player = (Player) sender;
        Inventory inv = player.getInventory();
        ItemStack spawnerWand = new ItemStack(Material.SHEARS);

        ItemMeta itemMeta = spawnerWand.getItemMeta();
        itemMeta.getPersistentDataContainer().set(ItemData.spawnerWandToggle, PersistentDataType.INTEGER, 0);
        itemMeta.setDisplayName(Text.colorize("&eSpawner Wand"));

        spawnerWand.setItemMeta(itemMeta);

        inv.setItem(inv.firstEmpty(), spawnerWand);
        return true;
    }
}
