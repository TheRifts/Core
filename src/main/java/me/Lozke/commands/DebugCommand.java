package me.Lozke.commands;

import me.Lozke.data.Rarity;
import me.Lozke.data.Tier;
import me.Lozke.handlers.ItemHandler;
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
        int amount = 1;
        Rarity rarity = Rarity.COMMON;

        if (args.length > 0) {
            num = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            type = args[1];
        }
        if (args.length > 2) {
            amount = Integer.parseInt(args[2]);
        }
        if (args.length > 3) {
            for (Rarity rarities : Rarity.types) {
                if (rarities.ordinal() == Integer.parseInt(args[3])) {
                    rarity = rarities;
                }
            }
        }

        for (Tier tier : Tier.types) {
            if (tier.ordinal() == num - 1) {
                switch (type) {
                    case "orb":
                        inv.setItem(inv.firstEmpty(), ItemHandler.newOrb(tier, amount));
                        return true;
                    case "scrap":
                        inv.setItem(inv.firstEmpty(), ItemHandler.newScrap(tier, amount));
                        return true;
                    case "sword":
                        inv.setItem(inv.firstEmpty(), ItemHandler.getWeapon(tier, rarity, "SWORD"));
                        return true;
                    case "axe":
                        inv.setItem(inv.firstEmpty(), ItemHandler.getWeapon(tier, rarity, "AXE"));
                        return true;
                    case "shovel":
                        inv.setItem(inv.firstEmpty(), ItemHandler.getWeapon(tier, rarity, "SHOVEL"));
                        return true;
                    case "hoe":
                        inv.setItem(inv.firstEmpty(), ItemHandler.getWeapon(tier, rarity, "HOE"));
                        return true;
                    case "boots":
                        inv.setItem(inv.firstEmpty(), ItemHandler.newBoots(tier,  rarity));
                        return true;
                    case "leggings":
                        inv.setItem(inv.firstEmpty(), ItemHandler.newLeggings(tier,  rarity));
                        return true;
                    case "chestplate":
                        inv.setItem(inv.firstEmpty(), ItemHandler.newChestplate(tier,  rarity));
                        return true;
                    case "helmet":
                        inv.setItem(inv.firstEmpty(), ItemHandler.newHelmet(tier,  rarity));
                        return true;
                    default:
                        for (ItemStack item : ItemHandler.newSet(tier,  rarity)) {
                            inv.setItem(inv.firstEmpty(), item);
                        }
                        return true;
                }
            }
        }
        return true;
    }
}
