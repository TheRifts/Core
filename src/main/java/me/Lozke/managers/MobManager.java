package me.Lozke.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import me.Lozke.RetardRealms;
import me.Lozke.data.MobSpawner;
import me.Lozke.data.Rarity;
import me.Lozke.data.Tier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MobManager {

    private RetardRealms plugin;

    private List<MobSpawner> mobSpawners;
    private boolean visible;

    public MobManager(RetardRealms plugin) {
        this.plugin = plugin;
        mobSpawners = new ArrayList<>();
        visible = false;
    }

    public void loadSpawners() {
        try {
            mobSpawners = new GsonBuilder().create().fromJson(new JsonReader(new FileReader(plugin.getDataFolder() + "/Spawners.json")), new TypeToken<List<MobSpawner>>() {}.getType());
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void saveSpawners() {
        try (FileWriter writer = new FileWriter(new File(plugin.getDataFolder() + "/Spawners.json"))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(mobSpawners, writer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    //Make this Async
    public void showSpawners() {
        visible = true;
        for (MobSpawner spawner : mobSpawners) {
            spawner.showSpawner();
        }
    }

    //Make this Async
    public void hideSpawners() {
        visible = false;
        for (MobSpawner spawner : mobSpawners) {
            spawner.getLocation().getBlock().setType(Material.AIR);
        }
    }

    public boolean visible() {
        return visible;
    }

    public void createSpawner(Location location) {
        mobSpawners.add(new MobSpawner(location, Tier.T1, Rarity.ANCIENT,"Mr. Poopy the Butthole", false, true, 42069).showSpawner());
    }

    //Make this async?
    public MobSpawner getSpawner(Location location) {
        for (MobSpawner spawner : mobSpawners) {
            if (spawner.getLocation().equals(location)) {
                return spawner;
            }
        }
        return null;
    }

    public void removeSpawner(Location location) {
        if (isSpawner(location)) {
            location.getBlock().setType(Material.AIR);
            getSpawner(location).cancelTask();
            mobSpawners.remove(getSpawner(location));
        }
    }

    //This method needs a better naming convention
    public boolean isSpawner(Location location) {
        return getSpawner(location) != null;
    }

    public Inventory openGUI(Location location) {
        return getSpawner(location).editor();
    }
}
