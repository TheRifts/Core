package me.Lozke.commands;

import me.Lozke.data.items.NamespacedKeys;
import me.Lozke.utils.Items;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CheckCommand extends Command {

    public CheckCommand() {
        super("verify");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
        boolean isRealItem = Items.isRealItem(item);
        if(isRealItem) {
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            player.sendMessage(container.get(NamespacedKeys.realItem, PersistentDataType.STRING));
        }
        else {
            player.sendMessage("This is NOT a FallingAutism™ Item!");
        }
        return isRealItem;
    }
}
