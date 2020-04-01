package me.Lozke.commands;

import me.Lozke.data.Tier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DebugCommand extends Command {

    public DebugCommand() {
        super("test");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player player = (Player) sender;
        Inventory inv = player.getInventory();
        int num = 0;
        String type = "";

        if (args.length == 1 ) {
            num = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            num = Integer.parseInt(args[0]);
            type = args[1];
        }

        for (Tier tier : Tier.types) {
            if (tier.ordinal() == num - 1) {
                switch (type) {
                    case "sword":
                        inv.setItem(inv.firstEmpty(), tier.getWeapon("SWORD"));
                        return true;
                    case "axe":
                        inv.setItem(inv.firstEmpty(), tier.getWeapon("AXE"));
                        return true;
                    case "shovel":
                        inv.setItem(inv.firstEmpty(), tier.getWeapon("SHOVEL"));
                        return true;
                    case "hoe":
                        inv.setItem(inv.firstEmpty(), tier.getWeapon("HOE"));
                        return true;
                    case "boots":
                        inv.setItem(inv.firstEmpty(), tier.getBoots());
                        return true;
                    case "leggings":
                        inv.setItem(inv.firstEmpty(), tier.getLeggings());
                        return true;
                    case "chestplate":
                        inv.setItem(inv.firstEmpty(), tier.getChestplate());
                        return true;
                    case "helmet":
                        inv.setItem(inv.firstEmpty(), tier.getHelmet());
                        return true;
                    default:
                        for (ItemStack item : tier.getSet()) {
                            inv.setItem(inv.firstEmpty(), item);
                        }
                        return true;
                }
            }
        }
        return true;
    }
}
