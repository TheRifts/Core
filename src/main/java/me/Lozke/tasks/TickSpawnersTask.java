package me.Lozke.tasks;

import me.Lozke.RetardRealms;
import me.Lozke.data.MobSpawner;
import me.Lozke.managers.MobManager;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TickSpawnersTask extends BukkitRunnable {

    private MobManager mobManager;

    public TickSpawnersTask(MobManager mobManager) {
        this.mobManager = mobManager;
        runTaskTimerAsynchronously(RetardRealms.getPluginInstance(), 0L, 20L);
    }

    @Override
    public void run() {
        for (MobSpawner spawner : mobManager.getSpawners()) {
            if (!spawner.isSpawnerActive()) { //return method, if spawner is off.
                return;
            }
            int timeLeft = spawner.getTimeLeft();
            if (timeLeft == 0) {
                Location[] locations = new Location[spawner.getAmount()];
                for (int i = 0; i < spawner.getAmount(); i++) {
                    Random rnd = new Random();
                    double a = rnd.nextDouble() * 2 * Math.PI;
                    double dist = rnd.nextDouble() * spawner.getRadius();
                    locations[i] = spawner.getLocation().clone().add(dist * Math.sin(a), 0, dist * Math.cos(a)).add(0.5, 0, 0.5);
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Location loc : locations) {
                            LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                            entity.setHealth(1);
                        }
                    }
                }.runTask(RetardRealms.getPluginInstance());
            }
            timeLeft = timeLeft > 0 ? timeLeft-1 : spawner.getSpawnTime()-1;
            spawner.setTimeLeft(timeLeft);
        }
    }
}
