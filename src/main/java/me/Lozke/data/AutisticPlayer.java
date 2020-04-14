/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.data;

import me.Lozke.FallingAutism;
import me.Lozke.utils.Logger;

import java.util.*;

public class AutisticPlayer {
    public static final int baseHP = 50;

    private Map<TimedPlayerStatus, Long> playerStatusMap;

    private final UUID uniqueId;

    public AutisticPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.playerStatusMap = new HashMap<>();
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Map<TimedPlayerStatus, Long> getStatusMap() {
        return playerStatusMap;
    }

    public Set<TimedPlayerStatus> getStatuses() {
        return playerStatusMap.keySet();
    }

    public void setStatus(TimedPlayerStatus status) {
        if (playerStatusMap.containsKey(status)) {
            playerStatusMap.put(status, status.getTicks());
        }
        else {
            playerStatusMap.put(status, status.getTicks());
        }
        FallingAutism.getPluginInstance().getPlayerManager().setStatus(uniqueId, status);
    }

    public long getStatusTime(TimedPlayerStatus status) {
        return playerStatusMap.get(status);
    }

    public void removeStatus(TimedPlayerStatus status) {
        playerStatusMap.remove(status);
        FallingAutism.getPluginInstance().getPlayerManager().removeStatus(uniqueId, status);
    }
}
