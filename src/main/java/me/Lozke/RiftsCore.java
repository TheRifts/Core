package me.Lozke;

import co.aikar.commands.BukkitCommandManager;
import me.Lozke.commands.ActionBarMessageCommand;
import me.Lozke.commands.BossBarCommand;
import me.Lozke.handlers.BossBarHandler;
import me.Lozke.listeners.ChunkMonitor;
import me.Lozke.listeners.DamagedEntityListener;
import me.Lozke.listeners.PacketParticleListener;
import me.Lozke.listeners.ReloadListener;
import me.Lozke.managers.ChunkManager;
import me.Lozke.tasks.ActionBarMessenger;
import me.Lozke.utils.ItemMenu.listeners.MenuClickListener;
import me.Lozke.utils.Logger;
import me.Lozke.utils.config.VersionedConfiguration;
import me.Lozke.utils.config.VersionedSmartYamlConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class RiftsCore extends JavaPlugin {

    private static RiftsCore plugin;

    private ChunkManager chunkManager;
    private BossBarHandler bossBarHandler;
    private ActionBarMessenger actionBarMessenger;

    @Override
    public void onEnable() {
        plugin = this;

        for (World world : getServer().getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.DISABLE_RAIDS, true);
            world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setGameRule(GameRule.DO_INSOMNIA, false);
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            world.setGameRule(GameRule.DO_MOB_LOOT, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            world.setGameRule(GameRule.DO_TILE_DROPS, false);
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, 0);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setGameRule(GameRule.SPAWN_RADIUS, 0);
            world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        }

        bossBarHandler = new BossBarHandler(this);
        actionBarMessenger = new ActionBarMessenger(this);
        chunkManager = new ChunkManager();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DamagedEntityListener(this), this);
        pm.registerEvents(new ChunkMonitor(chunkManager), this);
        pm.registerEvents(new ReloadListener(), this);
        pm.registerEvents(new MenuClickListener(), this);
        new PacketParticleListener(this);

        BukkitCommandManager cM = new BukkitCommandManager(this);
        cM.registerCommand(new BossBarCommand());
        cM.registerCommand(new ActionBarMessageCommand());

        Logger.log("The little monkeys have clocked in (\u001b[32mPlugin Enabled\u001b[0m)");
    }

    @Override
    public void onDisable() {
        bossBarHandler.removeAll();
        Bukkit.getScheduler().cancelTasks(this);
        Logger.log("The monkeys have left the building (\u001b[31mPlugin Disabled\u001b[0m)");
    }

    private VersionedSmartYamlConfiguration defaultSettingsLoad(String name) {
        return new VersionedSmartYamlConfiguration(new File(getDataFolder(), name),
                getResource(name), VersionedConfiguration.VersionUpdateType.BACKUP_AND_UPDATE);
    }

    public static RiftsCore getPluginInstance() {
        return plugin;
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public BossBarHandler getBossBarHandler() {
        return bossBarHandler;
    }

    public ActionBarMessenger getActionBarMessenger() {
        return actionBarMessenger;
    }
}
