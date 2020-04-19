/*
 * Created by Noah Pritchard on 4/15/2020
 */
package me.Lozke.tasks;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class EnergyRegenTask extends BukkitRunnable {
    private AutisticPlayer autisticPlayer;

    public EnergyRegenTask(AutisticPlayer autisticPlayer) {
        this.autisticPlayer = autisticPlayer;
        runTaskTimerAsynchronously(FallingAutism.getPluginInstance(), 2L, 2L);
    }

    @Override
    public void run() {
        autisticPlayer.setEnergy(autisticPlayer.getEnergy()+autisticPlayer.getEnergyRegen());
    }
}
