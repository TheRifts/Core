package me.Lozke.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.Lozke.RiftsCore;
import org.bukkit.entity.Player;

@CommandAlias("showbossbar")
public class BossBarCommand extends BaseCommand {

    @Default
    public static void execute(Player player, boolean toggle) {
        if (toggle) RiftsCore.getPluginInstance().getBossBarHandler().createBar(player);
        else RiftsCore.getPluginInstance().getBossBarHandler().removeBar(player);
    }
}
