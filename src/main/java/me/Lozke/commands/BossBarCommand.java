package me.Lozke.commands;

import me.Lozke.AgorianRifts;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BossBarCommand extends Command {

    public BossBarCommand() {
        super("bossbar");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player player = (Player) sender;

        if (args[0].equals("enable")) {
            AgorianRifts.getPluginInstance().getBossBarHandler().createBar(player);
        }

        if (args[0].equals("disable")) {
           AgorianRifts.getPluginInstance().getBossBarHandler().removeBar(player);
        }

        return true;
    }
}
