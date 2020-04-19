/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.data;

import me.Lozke.FallingAutism;
import me.Lozke.tasks.EnergyRegenTask;
import me.Lozke.tasks.HPRegenTask;
import me.Lozke.tasks.TickStatusesTask;
import me.Lozke.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

import static org.bukkit.Bukkit.getScheduler;

public class AutisticPlayer {
    public static final float fullEnergy = 100;
    public static final int baseHP = 50;
    public static final int baseHPRegen = 5;
    public static final float baseEnergyRegen = (float)1.5;

    private final UUID uniqueId;

    private EnergyRegenTask energyRegenTask;
    private HPRegenTask hpRegenTask;
    private TickStatusesTask tickStatusesTask;

    private Map<TimedPlayerStatus, Integer> statusMap;

    private float energy;
    private float energyRegen;
    private int HPRegen;

    public AutisticPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;

        energyRegenTask = new EnergyRegenTask(this);
        //Want status map to exist before trying to run tasks using it
        this.statusMap = new HashMap<>();
        hpRegenTask = new HPRegenTask(this);
        tickStatusesTask = new TickStatusesTask(this);

        setEnergy(fullEnergy);
        HPRegen = baseHPRegen;
        energyRegen = baseEnergyRegen;
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
            hpRegenTask = new HPRegenTask(this);
        }
    }

    public boolean hasEnergy() {
        return energy != 0;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        if (energy > fullEnergy) {
            energy = fullEnergy;
        }
        if (energy < 0) {
            energy = 0;
        }
        this.energy = energy;
        int level = (int)energy;
        float exp = energy/fullEnergy;

        Player player = Bukkit.getPlayer(uniqueId);

        player.setLevel(level);
        player.setExp(exp);

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

    public int getHPRegen() {
        return HPRegen;
    }

    public void setHpRegen(int HPRegen) {
        this.HPRegen = HPRegen;
    }

    public float getEnergyRegen() {
        return energyRegen;
    }

    public void setEnergyRegen(float energyRegen) {
        this.energyRegen = energyRegen;
    }
}
