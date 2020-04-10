package me.Lozke.commands;

import me.Lozke.FallingAutism;
import me.Lozke.managers.MobManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawners extends Command {

    private MobManager mobManager;

    public Spawners() {
        super("spawners");
        this.mobManager = FallingAutism.getPluginInstance().getMobManager();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        switch (args[0]) {
            case "save":
                mobManager.saveSpawners();
                break;
            case "load":
                mobManager.loadSpawners();
                break;
            case "show":
                mobManager.showSpawners();
                break;
            case "hide":
                mobManager.hideSpawners();
                break;
            case "edit":
                Player player = (Player) sender;
                Location location;
                try {
                    location = player.getTargetBlockExact(50).getLocation();
                } catch (NullPointerException ignore) {
                    break;
                }
                if (mobManager.isSpawner(location)) {
                    player.openInventory(mobManager.openGUI(location));
                }
                break;
        }
        return true;
    }
}
