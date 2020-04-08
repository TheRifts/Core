package me.Lozke.commands;

import me.Lozke.data.items.NamespacedKeys;
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
        ItemStack item = player.getItemInHand();
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (container.has(NamespacedKeys.realItem, PersistentDataType.STRING)) {
            player.sendMessage(container.get(NamespacedKeys.realItem, PersistentDataType.STRING));
            return true;
        }
        player.sendMessage("This is NOT a RetardedRealms™ Item!");
        return true;
    }
}
