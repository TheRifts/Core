/*
 * Created by Noah Pritchard on 4/15/2020
 */
package me.Lozke.tasks;

import me.Lozke.FallingAutism;
import me.Lozke.data.AutisticPlayer;
import me.Lozke.utils.Logger;
import org.bukkit.scheduler.BukkitRunnable;

public class EnergyRegenTask extends BukkitRunnable {
    private static int baseEnergyRegen = 5;

    private AutisticPlayer autisticPlayer;

    public EnergyRegenTask(AutisticPlayer autisticPlayer) {
        this.autisticPlayer = autisticPlayer;
        runTaskTimerAsynchronously(FallingAutism.getPluginInstance(), 4L, 4L);
    }

    @Override
    public void run() {
        autisticPlayer.setEnergy(autisticPlayer.getEnergy()+baseEnergyRegen);
    }

    @Override
    public void cancel() {
        super.cancel();
    }
}
