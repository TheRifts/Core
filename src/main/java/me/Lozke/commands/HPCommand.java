package me.Lozke.commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HPCommand extends Command {

    public HPCommand() {
        super("HP");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player player = (Player) sender;
        double num = 0;
        AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (args[0].equals("add")) {
            num = Double.parseDouble(args[1]);
            maxHealth.setBaseValue(maxHealth.getValue() + num);
            return true;
        }
        if (args[0].equals("remove") || args[0].equals("subtract")) {
            num = Double.parseDouble(args[1]);
            maxHealth.setBaseValue(maxHealth.getValue() - num);
            return true;
        }
        if (args[0].equals("check")) {
            player.sendMessage("Your MAX HP is currently at: " + maxHealth.getValue());
            return true;
        }
        return true;
    }
}
