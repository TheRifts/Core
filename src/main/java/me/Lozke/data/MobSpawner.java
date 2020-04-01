package me.Lozke.data;

import me.Lozke.RetardRealms;
import me.Lozke.guis.SpawnerEditor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;

public class MobSpawner {

    private Map<String, Object> location;
    private Tier tier;
    private Rarity rarity;
    private String mobType; //Convert this to an Object
    private boolean eliteStatus;
    private boolean spawnerStatus;
    private int spawnTimer;
    private transient int timeLeft;
    private transient BukkitTask task;

    public MobSpawner(MobSpawner mobSpawner) {
        this.location = mobSpawner.getSerializedLocation();
        this.tier = mobSpawner.getTier();
        this.rarity = mobSpawner.getRarity();
        this.mobType = mobSpawner.getMobType();
        this.eliteStatus = mobSpawner.getEliteStatus();
        this.spawnerStatus = mobSpawner.getSpawnerStatus();
        this.spawnTimer = mobSpawner.getSpawnTimer();
        this.timeLeft = spawnTimer;
        runTask();
    }

    public MobSpawner(Location location, Tier tier, Rarity rarity, String mobType, boolean eliteStatus, boolean spawnerStatus, int timer) {
        this.location = location.serialize();
        this.tier = tier;
        this.rarity = rarity;
        this.mobType = mobType;
        this.eliteStatus = eliteStatus;
        this.spawnerStatus = spawnerStatus;
        this.spawnTimer = timer;
        this.timeLeft = spawnTimer;
        runTask();
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
        showSpawner();
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getMobType() {
        return mobType;
    }

    public void setMobType(String mobType) {
        this.mobType = mobType;
    }

    public boolean getEliteStatus() {
        return eliteStatus;
    }

    public void toggleEliteStatus() {
        this.eliteStatus = !eliteStatus;
        showSpawner();
    }

    public boolean getSpawnerStatus() {
        return spawnerStatus;
    }

    public void toggleSpawnerStatus() {
        this.spawnerStatus = !spawnerStatus;
        if (spawnerStatus) {
            runTask();
        }
        else {
            cancelTask();
        }
    }

    public int getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(int time) {
        this.spawnTimer = time;
        runTask();
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int time) {
        this.timeLeft = time;
    }

    public Location getLocation() {
        return Location.deserialize(location);
    }

    public Map<String, Object> getSerializedLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location.serialize();
    }

    public void setLocation(Map<String,Object> serialiezedLocation) {
        this.location = serialiezedLocation;
    }

    public MobSpawner copy() {
        return new MobSpawner(this);
    }

    public Inventory editor() {
        return new SpawnerEditor(this).getGui();
    }

    public MobSpawner showSpawner() {
        if (eliteStatus) {
            Location.deserialize(location).getBlock().setType(Material.getMaterial(tier.getMaterialColor() + "_STAINED_GLASS"));
        } else {
            Location.deserialize(location).getBlock().setType(Material.getMaterial(tier.getMaterialColor() + "_CONCRETE"));
        }
        return this;
    }

    public void cancelTask() {
        task.cancel();
    }

    public void runTask() {
        if (task != null) {
            task.cancel();
        }
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (timeLeft == 0) {
                    //TODO: spawnerTask.run()
                }
                timeLeft = timeLeft-- > 0 ? timeLeft-- : spawnTimer;

            }
        }.runTaskTimer(RetardRealms.getPluginInstance(), 20, 20);
    }
}
