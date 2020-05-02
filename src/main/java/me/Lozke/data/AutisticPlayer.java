/*
 * Created by Noah Pritchard on 4/10/2020
 */
package me.Lozke.data;

import me.Lozke.tasks.EnergyRegenTask;
import me.Lozke.tasks.HPRegenTask;
import me.Lozke.tasks.TickStatusTask;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AutisticPlayer {

    private final UUID uuid;

    private EnergyRegenTask energyRegenTask;
    private HPRegenTask hpRegenTask;

    private ConcurrentHashMap<TimedPlayerStatus, TickStatusTask> statuses;

    private float energy;
    private float energyRegen;
    private int hpRegen;


    public AutisticPlayer(UUID uuid, int HPRegen, float energyRegen, float fullEnergy) {
        this.uuid = uuid;

        this.energy = fullEnergy;
        this.energyRegen = energyRegen;
        this.hpRegen = HPRegen;

        energyRegenTask = new EnergyRegenTask(Bukkit.getServer().getPlayer(uuid));
        //Want status map to exist before trying to run tasks using it
        statuses = new ConcurrentHashMap<>();
        hpRegenTask = new HPRegenTask(this);
    }


    public UUID getUUID() {
        return uuid;
    }

    public void setEnergyRegenTask(EnergyRegenTask energyRegenTask) {
        this.energyRegenTask = energyRegenTask;
    }
    public EnergyRegenTask getEnergyRegenTask() {
        return energyRegenTask;
    }

    public ConcurrentHashMap<TimedPlayerStatus, TickStatusTask> getStatusMap() {
        return statuses;
    }
    public void setStatusMap(ConcurrentHashMap<TimedPlayerStatus, TickStatusTask> map) {
        this.statuses = map;
    }
    public void addStatus(TimedPlayerStatus status) {
        if (statuses.containsKey(status)) {
            if(status.isExtendable()) {
                statuses.get(status).setExtended();
                statuses.get(status).cancel();
                statuses.put(status, new TickStatusTask(status, this));
            }
        }
        //Status is not already active
        else {
            statuses.put(status, new TickStatusTask(status, this));

            //Handle hp regen
            if(status == TimedPlayerStatus.MOB_COMBAT || status == TimedPlayerStatus.PLAYER_COMBAT) {
                hpRegenTask.cancel();
            }
        }
    }
    public void removeStatus(TimedPlayerStatus status) {
        statuses.get(status).cancel();
        statuses.remove(status);
        //Handle hp regen
        if(!statuses.containsKey(TimedPlayerStatus.MOB_COMBAT) && !statuses.containsKey(TimedPlayerStatus.PLAYER_COMBAT)) {
            hpRegenTask = new HPRegenTask(this);
        }
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }
    public float getEnergy() {
        return energy;
    }

    public void setHpRegen(int HPRegen) {
        this.hpRegen = HPRegen;
    }
    public int getHpRegen() {
        return hpRegen;
    }

    public void setEnergyRegen(float energyRegen) {
        this.energyRegen = energyRegen;
    }
    public float getEnergyRegen() {
        return energyRegen;
    }


    public void handleLogout() {
        //Must come first as removeStatus may restart hpRegenTask
        for (TimedPlayerStatus status : statuses.keySet()) {
            removeStatus(status);
        }

        energyRegenTask.cancel();
        hpRegenTask.cancel();
    }
}
