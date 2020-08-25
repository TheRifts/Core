package me.Lozke.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.Lozke.AgorianRifts;
import me.Lozke.data.ActionBarType.ActionBarFixedMessage;
import me.Lozke.data.ActionBarType.ActionBarMessage;
import me.Lozke.data.ActionBarType.ActionBarTimerMessage;
import me.Lozke.data.ActionBarType.ActionBarToggleMessage;
import org.bukkit.entity.Player;

@CommandAlias("actionbar")
public class ActionBarMessageCommand extends BaseCommand {

    private static void addBar(Player player, ActionBarMessage message) {
        AgorianRifts.getPluginInstance().getActionBarMessenger().addMessage(player, message);
    }

    @Default
    public static void onCommand(Player player, int time, String string) {
        ActionBarMessage barMessage = new ActionBarMessage(string, string, time);
        addBar(player, barMessage);
    }

    @Subcommand("toggle")
    public static void onToggle(Player player, int time, String string) {
        ActionBarToggleMessage barMessage = new ActionBarToggleMessage(string,string, string + " [toggled]", time);
        addBar(player, barMessage);
    }

    @Subcommand("timer")
    public static void onTimer(Player player, int time, String string) {
        ActionBarTimerMessage barMessage = new ActionBarTimerMessage(string, string, time, true);
        addBar(player, barMessage);
    }

    @Subcommand("fixed")
    public static void onFixed(Player player, String string) {
        ActionBarFixedMessage barMessage = new ActionBarFixedMessage(string, string);
        addBar(player, barMessage);
    }

    @Subcommand("remove")
    public static void onRemove(Player player, String string) {
        AgorianRifts.getPluginInstance().getActionBarMessenger().removeMessage(player.getUniqueId(), string);
    }

}
