package me.Lozke.tasks;

import me.Lozke.RetardRealms;
import me.Lozke.managers.MobManager;
import org.bukkit.scheduler.BukkitRunnable;

public class TickSpawnersTask extends BukkitRunnable {

    private MobManager mobManager;

    public TickSpawnersTask(MobManager mobManager) {
        this.mobManager = mobManager;
        runTaskTimer(RetardRealms.getPluginInstance(), 0L, 20L);
    }

    @Override
    public void run() {
        mobManager.tickSpawners();
    }
}
