package me.Lozke;

import me.Lozke.commands.*;
import me.Lozke.events.*;
import me.Lozke.handlers.BossBarHandler;
import me.Lozke.managers.MobManager;
import me.Lozke.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.List;

public class FallingAutism extends JavaPlugin {

    private static FallingAutism plugin;
    private List<World> playWorlds;
    private MobManager mobManager;
    private PlayerManager playerManager;
    private BossBarHandler bossBarHandler;

    private static FileConfiguration gearData;

    @Override
    public void onEnable() {
        plugin = this;
        playWorlds = getServer().getWorlds();

        gearData = this.getConfig();

        mobManager = new MobManager(this);
        mobManager.loadSpawners();
        playerManager = new PlayerManager();
        bossBarHandler = new BossBarHandler(this);

        for (World world : playWorlds) {
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

        //Turn this into the same setup as commands
        //Turn this into a Factory
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerLoginListener(this), this);
        pm.registerEvents(new PlayerLogoutListener(this), this);
        pm.registerEvents(new ArmourChangeListener(), this);
        pm.registerEvents(new ItemInteractionListener(), this);
        pm.registerEvents(new SpawnerWandToggleListener(), this);
        pm.registerEvents(new DamageListener(this), this);
        pm.registerEvents(new PlayerDeathListener(this), this);
        pm.registerEvents(new PlayerRespawnListener(this), this);

        //Migrate this to a Factory
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(this.getName(), new DebugCommand());
            commandMap.register(this.getName(), new BossBarCommand());
            commandMap.register(this.getName(), new CheckCommand());
            commandMap.register(this.getName(), new HPCommand());
            commandMap.register(this.getName(), new ItemRename());
            commandMap.register(this.getName(), new Reload());
            commandMap.register(this.getName(), new Spawners());
            commandMap.register(this.getName(), new SpawnerWand());
            commandMap.register(this.getName(), new SpawnMob());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("[FallingAutism] The little monkeys have clocked in (\u001b[32mPlugin Enabled\u001b[0m)");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        bossBarHandler.removeAll();
        mobManager.saveSpawners();
        mobManager.hideSpawners();
        playerManager.saveAllPlayers();
        System.out.println("[FallingAutisma] The monkeys have left the building (\u001b[31mPlugin Disabled\u001b[0m)");
    }

    public static FallingAutism getPluginInstance() {
        return plugin;
    }

    public BossBarHandler getBossBarHandler() {
        return bossBarHandler;
    }

    public static FileConfiguration getGearData() {
        return gearData;
    }

    public MobManager getMobManager() {
        return mobManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
