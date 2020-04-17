/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.managers;

import me.Lozke.data.AutisticPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    private Map<UUID, AutisticPlayer> autisticPlayers;

    public PlayerManager() {
        autisticPlayers = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            loadPlayer(player);
        }
    }

    public AutisticPlayer getPlayer(UUID uniqueId) {
        return autisticPlayers.get(uniqueId);
    }

    public List<AutisticPlayer> getPlayers() {
        return new ArrayList<>(autisticPlayers.values());
    }

    public AutisticPlayer addPlayer(Player player) {
        return addPlayer(player.getUniqueId());
    }

    public AutisticPlayer addPlayer(UUID uniqueId) {
        AutisticPlayer autisticPlayer = new AutisticPlayer(uniqueId);
        autisticPlayers.put(uniqueId, autisticPlayer);
        return autisticPlayer;
    }

    public void removePlayer(Player player) {
        removePlayer(player.getUniqueId());
    }

    public void removePlayer(UUID uniqueId) {
        autisticPlayers.remove(uniqueId);
    }

    public void loadPlayer(Player player) {
        loadPlayer(player.getUniqueId());
    }

    public void loadPlayer(UUID uniqueId) {
        //TODO actually load player data
        if (!autisticPlayers.containsKey(uniqueId)) {
            addPlayer(uniqueId);
        }
    }

    public void savePlayer(Player player) {
        savePlayer(player.getUniqueId());
    }

    public void savePlayer(UUID uniqueId) {
        //TODO actually save player data
    }

    public void saveAllPlayers() {
        for (UUID uniqueId : autisticPlayers.keySet()) {
            savePlayer(uniqueId);
        }
    }
}
