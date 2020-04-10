package me.Lozke.commands;

import me.Lozke.FallingAutism;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Reload extends Command {

    public Reload() {
        super("autisticreload");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        FallingAutism.getPluginInstance().onDisable();
        FallingAutism.getPluginInstance().onEnable();
        return true;
    }
}
