/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.tasks;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.TimedPlayerStatus;
import me.Lozke.managers.PlayerManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.List;

import static org.bukkit.Bukkit.getScheduler;

public class TickStatusesTask extends BukkitRunnable {
    public static final long period = 1;

    private Map<UUID, List<TimedPlayerStatus>> newStatuses;
    private Map<UUID, List<TimedPlayerStatus>> recentlyEndedStatuses;

    private PlayerManager playerManager;

    public TickStatusesTask(PlayerManager playerManager) {
        this.playerManager = playerManager;

        newStatuses = new HashMap<>();
        recentlyEndedStatuses = new HashMap<>();

        runTaskTimerAsynchronously(FallingAutism.getPluginInstance(), 0L, period);
    }

    public void addNewStatus(UUID UUID, TimedPlayerStatus status) {
        if (newStatuses.containsKey(UUID)) {
            newStatuses.get(UUID).add(status);
        }
        else {
            List<TimedPlayerStatus> statuses = new ArrayList<>();
            statuses.add(status);
            newStatuses.put(UUID, statuses);
        }
    }

    public void addRecentlyEndedStatus(UUID uniqueId, TimedPlayerStatus status) {
        if(recentlyEndedStatuses.containsKey(uniqueId)) {
            recentlyEndedStatuses.get(uniqueId).add(status);
        }
        else {
            List<TimedPlayerStatus> statuses = new ArrayList<>();
            statuses.add(status);
            recentlyEndedStatuses.put(uniqueId, statuses);
        }

        getScheduler().scheduleSyncDelayedTask(FallingAutism.getPluginInstance(), new Runnable() {
            @Override
            public void run() {
                removeRecentlyEndedStatus(uniqueId, status);
            }
        }, 10L);
    }

    private void removeRecentlyEndedStatus(UUID uniqueId, TimedPlayerStatus status) {
        if(this.recentlyEndedStatuses.containsKey(uniqueId)) {
            List<TimedPlayerStatus> recentlyEndedStatuses = this.recentlyEndedStatuses.get(uniqueId);
            recentlyEndedStatuses.remove(status);
            if(recentlyEndedStatuses.isEmpty()) {
                this.recentlyEndedStatuses.remove(uniqueId);
            }
        }
    }

    private boolean checkForNewStatuses(UUID uniqueId) {
        if(this.newStatuses.containsKey(uniqueId)) {
            this.newStatuses.remove(uniqueId);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        for (AutisticPlayer autisticPlayer : playerManager.getPlayers()) {
            Map<TimedPlayerStatus, Long> playerStatusMap = autisticPlayer.getStatusMap();
            UUID uniqueId = autisticPlayer.getUniqueId();
            if (!playerStatusMap.isEmpty() || recentlyEndedStatuses.containsKey(uniqueId)) {
                boolean sendMessage = false;
                List<TimedPlayerStatus> endedPlayerStatuses = new ArrayList<>();

                for (TimedPlayerStatus status : playerStatusMap.keySet()) {
                    long ticks = playerStatusMap.get(status);
                    if (ticks > 0) {
                        //Prevent ticks going negative
                        if(ticks < period) {
                            ticks = 0;
                        }
                        //Handle normal tick
                        else {
                            ticks = ticks - period;
                        }
                        //Statuses that reach 0 need to be removed immediately and saved
                        if(ticks == 0) {
                            endedPlayerStatuses.add(status);
                            addRecentlyEndedStatus(uniqueId, status);
                        }
                        //Otherwise update with the remaining ticks
                        else {
                            playerStatusMap.put(status, ticks);
                        }
                        //Only need to display message if one of the statuses is changing on the second scale
                        if(ticks%20 == 0) {
                            sendMessage = true;
                        }
                    }
                }

                //Remove statuses that have ended from the player (can't be done in previous loop - concurrent access)
                for(TimedPlayerStatus status : endedPlayerStatuses) {
                    autisticPlayer.removeStatus(status);
                }

                //Ensure that freshly posted statuses get a message
                boolean newStatuses = checkForNewStatuses(uniqueId);
                if(!sendMessage) {
                    sendMessage = newStatuses;
                }

                if (sendMessage) {
                    StringBuilder message = new StringBuilder();
                    for (TimedPlayerStatus status: TimedPlayerStatus.values()) {
                        if (playerStatusMap.containsKey(status)) {
                            long ticks = playerStatusMap.get(status);
                            long secondsRemaining = ticks/20;
                            //Account for integer divison rounding down
                            if(ticks%20 != 0) {
                                secondsRemaining++;
                            }
                            message.append(status.getMessage())
                                    .append(TimedPlayerStatus.timePrefix)
                                    .append(secondsRemaining)
                                    .append(TimedPlayerStatus.timeSuffix)
                                    .append(TimedPlayerStatus.spacer);
                        }
                        if (recentlyEndedStatuses.containsKey(uniqueId) && recentlyEndedStatuses.get(uniqueId).contains(status)) {
                            message.append(status.getEndMessage())
                                    .append(TimedPlayerStatus.spacer);
                        }
                    }

                    //Account for extra spacer placed at end
                    String messageAsString = message.toString();
                    String finalMessage = messageAsString.substring(0, messageAsString.length() - TimedPlayerStatus.spacer.length());

                    //Send the message to the player's hotbar
                    Bukkit.getPlayer(autisticPlayer.getUniqueId()).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(finalMessage));
                }
            }
        }
    }
}
