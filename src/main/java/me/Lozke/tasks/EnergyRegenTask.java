/*
 * Created by Noah Pritchard on 4/15/2020
 */
package me.Lozke.tasks;

import me.Lozke.FallingAutism;
import me.Lozke.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnergyRegenTask extends BukkitRunnable {

    private Player player;
    private PlayerManager manager;


    public EnergyRegenTask(Player player) {
        this.player = player;
        this.manager = FallingAutism.getPluginInstance().getPlayerManager();
        runTaskTimerAsynchronously(FallingAutism.getPluginInstance(), 20L, 2L);
    }


    @Override
    public void run() {
        if (this.isCancelled() || manager == null) {
            this.cancel();
        }
        else {
            manager.updateEnergy(player, manager.getEnergy(player) + manager.getEnergyRegen(player));
        }
    }
}
