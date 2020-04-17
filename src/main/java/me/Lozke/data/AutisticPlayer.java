/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.data;

import me.Lozke.FallingAutism;
import me.Lozke.tasks.EnergyRegenTask;
import me.Lozke.tasks.HpRegenTask;
import me.Lozke.tasks.TickStatusesTask;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

import static org.bukkit.Bukkit.getScheduler;

public class AutisticPlayer {
    public static final int fullEnergy = 100;
    public static final int baseHP = 50;
    private final UUID uniqueId;

    private EnergyRegenTask energyRegenTask;
    private HpRegenTask hpRegenTask;
    private TickStatusesTask tickStatusesTask;

    private Map<TimedPlayerStatus, Integer> statusMap;

    private int energy;

    public AutisticPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;

        energyRegenTask = new EnergyRegenTask(this);
        setEnergy(fullEnergy);
        //Want status map to exist before trying to run tasks using it
        this.statusMap = new HashMap<>();
        hpRegenTask = new HpRegenTask(uniqueId);
        tickStatusesTask = new TickStatusesTask(this);
    }

    private AutisticPlayer getAutisticPlayerInstance() {
        return this;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Map<TimedPlayerStatus, Integer> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(Map<TimedPlayerStatus, Integer> statusMap) {
       this.statusMap = statusMap;
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

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        if (energy > fullEnergy) {
            energy = fullEnergy;
        }
        if (energy < 0) {
            energy = 0;
        }
        this.energy = energy;
        float energyAsFloat = energy/(float)fullEnergy;

        Player player = Bukkit.getPlayer(uniqueId);

        player.setLevel(energy);
        player.setExp(energyAsFloat);

        if (energy == 0) {
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, (float)0.7, (float)1.5);
            if(!energyRegenTask.isCancelled()) {
                energyRegenTask.cancel();
                getScheduler().scheduleSyncDelayedTask(FallingAutism.getPluginInstance(), new Runnable() {
                    @Override
                    public void run() {
                        energyRegenTask = new EnergyRegenTask(getAutisticPlayerInstance());
                    }
                }, 30L);
            }
        }
        else if (energy != fullEnergy && energyRegenTask.isCancelled()) {
            energyRegenTask = new EnergyRegenTask(this);
        }
        else if (energy == fullEnergy) {
            energyRegenTask.cancel();
        }
    }

    public void handleLogout() {
        energyRegenTask.cancel();
        hpRegenTask.cancel();
        tickStatusesTask.cancel();
    }
}
