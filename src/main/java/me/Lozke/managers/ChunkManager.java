package me.Lozke.managers;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public class ChunkManager {

    private final Map<String, Long> chunkActiveStamp = new HashMap<>();

    public ChunkManager() {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                stampChunk(chunk);
            }
        }
    }

    public void stampChunk(Chunk chunk) {
        chunkActiveStamp.put(chunk.getWorld().getName() + chunk.getChunkKey(), System.currentTimeMillis() + 1200);
    }

    public void unstampChunk(Chunk chunk) {
        chunkActiveStamp.remove(chunk.getWorld().getName() + chunk.getChunkKey());
    }

    public boolean isChuckLoaded(Location location, Long chunkKey) {
        return isChunkLoaded(location.getWorld(), chunkKey);
    }
    public boolean isChunkLoaded(World world, Long chunkKey) {
        String chunkId = world.getName() + chunkKey;
        if (chunkActiveStamp.containsKey(chunkId)) {
            return chunkActiveStamp.get(chunkId) < System.currentTimeMillis();
        }
        return false;
    }
}
