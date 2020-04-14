/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.managers;

import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.TimedPlayerStatus;
import me.Lozke.tasks.TickStatusesTask;

import java.util.*;

public class PlayerManager {

    private Map<UUID, AutisticPlayer> autisticPlayers;
    private TickStatusesTask tickStatusesTask;

    public PlayerManager() {
        autisticPlayers = new HashMap<>();
        tickStatusesTask = new TickStatusesTask(this);
    }

    public AutisticPlayer getPlayer(UUID uniqueId) {
        return autisticPlayers.get(uniqueId);
    }

    public List<AutisticPlayer> getPlayers() {
        return new ArrayList<>(autisticPlayers.values());
    }

    public AutisticPlayer addPlayer(UUID uniqueId) {
        AutisticPlayer autisticPlayer = new AutisticPlayer(uniqueId);
        autisticPlayers.put(uniqueId, autisticPlayer);
        return autisticPlayer;
    }

    public void loadPlayer(UUID uniqueId) {
        //TODO actually load player data
        if (!autisticPlayers.containsKey(uniqueId)) {
            addPlayer(uniqueId);
        }
    }

    public void savePlayer(UUID uniqueId) {
        //TODO actually save player data
        autisticPlayers.remove(uniqueId);
    }

    public void savePlayers() {
        for (UUID uniqueId : autisticPlayers.keySet()) {
            savePlayer(uniqueId);
        }
    }

    public void setStatus(UUID uniqueId, TimedPlayerStatus status) {
        tickStatusesTask.addNewStatus(uniqueId, status);
    }

    public void removeStatus(UUID uniqueId, TimedPlayerStatus status) {
        tickStatusesTask.addRecentlyEndedStatus(uniqueId, status);
    }
}
