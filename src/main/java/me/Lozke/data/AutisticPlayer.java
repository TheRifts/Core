/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.data;

import me.Lozke.tasks.HpRegenTask;
import me.Lozke.tasks.TickStatusesTask;

import java.util.*;

public class AutisticPlayer {
    public static final int baseHP = 50;

    private Map<TimedPlayerStatus, Integer> statusMap;

    private HpRegenTask hpRegenTask;
    private TickStatusesTask tickStatusesTask;

    private final UUID uniqueId;

    public AutisticPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.statusMap = new HashMap<>();
        hpRegenTask = new HpRegenTask(uniqueId);
        tickStatusesTask = new TickStatusesTask(this);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Map<TimedPlayerStatus, Integer> getStatusMap() {
        return statusMap;
    }

    public Set<TimedPlayerStatus> getStatuses() {
        return statusMap.keySet();
    }

    public long getStatusTime(TimedPlayerStatus status) {
        return statusMap.get(status);
    }

    public void setStatus(TimedPlayerStatus status) {
        if (statusMap.containsKey(status)) {
            if(status.isExtendable()) {
                statusMap.put(status, status.getSeconds());
            }
        }
        else {
            statusMap.put(status, status.getSeconds());

            //Handle hp regen
            if(status == TimedPlayerStatus.MOB_COMBAT || status == TimedPlayerStatus.PLAYER_COMBAT) {
                hpRegenTask.cancel();
            }
        }
        //Handle action bar output
        tickStatusesTask.sendMessage(statusMap);
    }

    public void removeStatus(TimedPlayerStatus status) {
        statusMap.remove(status);
        //Handle action bar output
        tickStatusesTask.handleRecentlyEndedStatus(status);
        tickStatusesTask.sendMessage(statusMap);
        //Handle hp regen
        if(!statusMap.containsKey(TimedPlayerStatus.MOB_COMBAT) && !statusMap.containsKey(TimedPlayerStatus.PLAYER_COMBAT)) {
            hpRegenTask = new HpRegenTask(uniqueId);
        }
    }

    public void handleLogout() {
        hpRegenTask.cancel();
        tickStatusesTask.cancel();
    }
}
