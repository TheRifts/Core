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

    public PlayerManager() {
        autisticPlayers = new HashMap<>();
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
        autisticPlayers.get(uniqueId).handleLogout();
        autisticPlayers.remove(uniqueId);
    }
}
