/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.tasks;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.TimedPlayerStatus;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.List;

import static org.bukkit.Bukkit.getScheduler;

public class TickStatusesTask extends BukkitRunnable {
    private AutisticPlayer autisticPlayer;

    private List<TimedPlayerStatus> recentlyEndedStatuses;

    public TickStatusesTask(AutisticPlayer autisticPlayer) {
        this.autisticPlayer = autisticPlayer;
        this.recentlyEndedStatuses = new ArrayList<>();

        runTaskTimerAsynchronously(FallingAutism.getPluginInstance(), 0L, 20L);
    }

    public void handleRecentlyEndedStatus(TimedPlayerStatus status) {
        recentlyEndedStatuses.add(status);

        getScheduler().scheduleSyncDelayedTask(FallingAutism.getPluginInstance(), new Runnable() {
            @Override
            public void run() {
                recentlyEndedStatuses.remove(status);
            }
        }, 10L);
    }

    @Override
    public void run() {
        Map<TimedPlayerStatus, Integer> playerStatusMap = autisticPlayer.getStatusMap();
        if (!playerStatusMap.isEmpty()) {
            List<TimedPlayerStatus> endedPlayerStatuses = new ArrayList<>();

            for (TimedPlayerStatus status : playerStatusMap.keySet()) {
                int seconds = playerStatusMap.get(status);
                if (seconds > 0) {
                    seconds--;
                    //Statuses that reach 0 need to be removed immediately and saved
                    if(seconds == 0) {
                        endedPlayerStatuses.add(status);
                        handleRecentlyEndedStatus(status);
                    }
                    //Otherwise update with the remaining seconds
                    else {
                        playerStatusMap.put(status, seconds);
                    }
                }
            }

            //Remove statuses that have ended from the player (can't be done in previous loop - concurrent access)
            for(TimedPlayerStatus status : endedPlayerStatuses) {
                autisticPlayer.removeStatus(status);
            }

            sendMessage(playerStatusMap);
        }
    }

    public void sendMessage(Map<TimedPlayerStatus, Integer> playerStatusMap) {
        StringBuilder message = new StringBuilder();
        for (TimedPlayerStatus status: TimedPlayerStatus.values()) {
            if (playerStatusMap.containsKey(status)) {
                message.append(status.getMessage())
                        .append(TimedPlayerStatus.timePrefix)
                        .append(playerStatusMap.get(status))
                        .append(TimedPlayerStatus.timeSuffix)
                        .append(TimedPlayerStatus.spacer);
            }
            if (recentlyEndedStatuses.contains(status)) {
                message.append(status.getEndMessage())
                        .append(TimedPlayerStatus.spacer);
            }
        }

        //Account for extra spacer placed at end
        String messageAsString = message.toString();
        String finalMessage = messageAsString.substring(0, messageAsString.length() - TimedPlayerStatus.spacer.length());

        //Send the message to the player's hotbar
        Player player = Bukkit.getPlayer(autisticPlayer.getUniqueId());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(finalMessage));
    }
}
