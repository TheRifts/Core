package me.Lozke.commands;

import me.Lozke.RetardRealms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Reload extends Command {

    public Reload() {
        super("retardedreload");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        RetardRealms.getPluginInstance().onDisable();
        RetardRealms.getPluginInstance().onEnable();
        return true;
    }
}
