package me.Lozke.listeners;

import me.Lozke.managers.ChunkManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkMonitor implements Listener {

    private ChunkManager manager;

    public ChunkMonitor(ChunkManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void chunkLoadMonitor(ChunkLoadEvent e) {
        manager.stampChunk(e.getChunk());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void chunkUnloadMonitor(ChunkUnloadEvent e) {
        manager.unstampChunk(e.getChunk());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChunkUnload(ChunkUnloadEvent e) {
        manager.unstampChunk(e.getChunk());
    }
}
