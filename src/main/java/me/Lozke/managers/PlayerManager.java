/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.managers;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.data.TimedPlayerStatus;
import me.Lozke.tasks.EnergyRegenTask;
import me.Lozke.tasks.TickStatusTask;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    public static final float fullEnergy = 100;
    public static final int baseHP = 50; //TODO place health under playerManager management
    public static final int baseHpRegen = 5;
    public static final float baseEnergyRegen = 1.5F;

    private Map<UUID, AutisticPlayer> autisticPlayers;


    public PlayerManager() {
        autisticPlayers = new ConcurrentHashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            loadPlayer(player);
        }
    }

    //PLAYERS
    public boolean isAutisticPlayer(Player player) {
        return isAutisticPlayer(player.getUniqueId());
    }
    public boolean isAutisticPlayer(UUID uuid) {
        return autisticPlayers.containsKey(uuid);
    }

    public AutisticPlayer getAutisticPlayer(Player player) {
        return getAutisticPlayer(player.getUniqueId());
    }
    public AutisticPlayer getAutisticPlayer(UUID uuid) {
        return autisticPlayers.get(uuid);
    }
    public List<AutisticPlayer> getAllPlayers() {
        return new ArrayList<>(autisticPlayers.values());
    }

    public AutisticPlayer addPlayer(Player player) {
        return addPlayer(player.getUniqueId());
    }
    public AutisticPlayer addPlayer(UUID uuid) {
        AutisticPlayer autisticPlayer = new AutisticPlayer(uuid, baseHpRegen, baseEnergyRegen, fullEnergy);
        autisticPlayers.put(uuid, autisticPlayer);
        return autisticPlayer;
    }

    public void removePlayer(Player player) {
        removePlayer(player.getUniqueId());
    }
    public void removePlayer(UUID uuid) {
        getAutisticPlayer(uuid).handleLogout();
        autisticPlayers.remove(uuid);
    }
    public void removeAllPlayers() {
        for (UUID uuid : autisticPlayers.keySet()) {
            removePlayer(uuid);
        }
    }

    public void loadPlayer(Player player) {
        loadPlayer(player.getUniqueId());
    }
    public void loadPlayer(UUID uuid) {
        //TODO actually load player data
        if (!autisticPlayers.containsKey(uuid)) {
            addPlayer(uuid);
        }
    }

    public void savePlayer(Player player) {
        savePlayer(player.getUniqueId());
    }
    public void savePlayer(UUID uuid) {
        //TODO actually save player data
    }
    public void saveAllPlayers() {
        for (UUID uuid : autisticPlayers.keySet()) {
            savePlayer(uuid);
        }
    }

    //ENERGY, ENERGY REGEN, and HP REGEN
    public float getEnergy(Player player) {
        return getEnergy(player.getUniqueId());
    }
    public float getEnergy(UUID uuid) {
        return getAutisticPlayer(uuid).getEnergy();
    }

    public boolean hasEnergy(Player player) {
        return hasEnergy(player.getUniqueId());
    }
    public boolean hasEnergy(UUID uuid) {
        return getEnergy(uuid) > 0;
    }

    public void updateEnergy(Player player, float energy) {
        updateEnergy(player.getUniqueId(), energy);
    }
    public void updateEnergy(UUID uuid, float newEnergy) {
        Player player = Bukkit.getServer().getPlayer(uuid);

        if (player == null) {
            getAutisticPlayer(uuid).handleLogout();
            return;
        }

        EnergyRegenTask task = getAutisticPlayer(player).getEnergyRegenTask();
        //Prevent energy overflow, stop regenerating energy when full
        if (newEnergy > fullEnergy) {
            newEnergy = fullEnergy;
            getAutisticPlayer(player).getEnergyRegenTask().cancel();
        }
        //Prevent energy underflow, stop regenerating energy when empty and start again after a delay
        else if (newEnergy <= 0) {
            newEnergy = 0;
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 0.7F, 1.5F); //TODO probably remove seems unnecessary due to later check
            if (!task.isCancelled()) {
                task.cancel();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        getAutisticPlayer(player).setEnergyRegenTask(new EnergyRegenTask(player));
                    }
                }.runTaskLater(FallingAutism.getPluginInstance(), 100L);
            }
        }
        //Start regenerating energy if new energy is in bounds
        else if (newEnergy != fullEnergy && getAutisticPlayer(player).getEnergyRegenTask().isCancelled()) {
            getAutisticPlayer(player).setEnergyRegenTask(new EnergyRegenTask(player));
        }

        getAutisticPlayer(player).setEnergy(newEnergy);

        //Play feedback sound if player is out of energy
        if (getEnergy(player) <= 0) {
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, .005F, 1.5F); //TODO add delay if still having it play for too long
        }

        //Update player's visual display
        int level = (int) newEnergy;
        float exp = newEnergy/fullEnergy;
        player.setLevel(level);
        player.setExp(exp);
    }

    public float getEnergyRegen(Player player) {
        return getEnergyRegen(player.getUniqueId());
    }
    public float getEnergyRegen(UUID uuid) {
        return getAutisticPlayer(uuid).getEnergyRegen();
    }

    public void updateEnergyRegen(Player player, float newEnergyRegen) {
        updateEnergyRegen(player.getUniqueId(), newEnergyRegen);
    }
    public void updateEnergyRegen(UUID uuid, float newEnergyRegen) {
        getAutisticPlayer(uuid).setEnergyRegen(newEnergyRegen);
    }

    public int getHPRegen(Player player) {
        return getHPRegen(player.getUniqueId());
    }
    public int getHPRegen(UUID uuid) {
        return getAutisticPlayer(uuid).getHpRegen();
    }

    public void updateHpRegen(Player player, int newHpRegen) {
        updateHpRegen(player.getUniqueId(), newHpRegen);
    }
    public void updateHpRegen(UUID uuid, int newHpRegen) {
        getAutisticPlayer(uuid).setHpRegen(newHpRegen);
    }

    //STATUSES
    public Map<TimedPlayerStatus, TickStatusTask> getAllStatuses(Player player) {
        return getAllStatuses(player.getUniqueId());
    }
    public Map<TimedPlayerStatus, TickStatusTask> getAllStatuses(UUID uuid) {
        return getAutisticPlayer(uuid).getStatusMap();
    }

    public void updateStatuses(Player player, ConcurrentHashMap<TimedPlayerStatus, TickStatusTask> map) {
        updateStatuses(player.getUniqueId(), map);
    }
    public void updateStatuses(UUID uuid, ConcurrentHashMap<TimedPlayerStatus, TickStatusTask> map) {
        getAutisticPlayer(uuid).setStatusMap(map);
    }

    public Set<TimedPlayerStatus> getStatuses(Player player) {
        return getStatuses(player.getUniqueId());
    }
    public Set<TimedPlayerStatus> getStatuses(UUID uuid) {
        return getAutisticPlayer(uuid).getStatusMap().keySet();
    }

    public boolean hasStatus(Player player, TimedPlayerStatus timedPlayerStatus) {
        return hasStatus(player.getUniqueId(), timedPlayerStatus);
    }
    public boolean hasStatus(UUID uuid, TimedPlayerStatus timedPlayerStatus) {
        return getAutisticPlayer(uuid).getStatusMap().containsKey(timedPlayerStatus);
    }

    public void updateStatus(Player player, TimedPlayerStatus timedPlayerStatus, boolean set) {
        updateStatus(player.getUniqueId(), timedPlayerStatus, set);
    }
    public void updateStatus(UUID uuid, TimedPlayerStatus timedPlayerStatus, boolean set) {
        if (set) {
            getAutisticPlayer(uuid).addStatus(timedPlayerStatus);
        }
        else {
            getAutisticPlayer(uuid).removeStatus(timedPlayerStatus);
        }
    }
}
