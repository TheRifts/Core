package me.Lozke;

import me.Lozke.commands.*;
import me.Lozke.listeners.*;
import me.Lozke.tasks.ActionBarMessenger;
import me.Lozke.handlers.BossBarHandler;
import me.Lozke.utils.ItemMenu.listeners.MenuClickListener;
import me.Lozke.utils.Logger;
import me.Lozke.utils.config.SmartYamlConfiguration;
import me.Lozke.utils.config.VersionedConfiguration;
import me.Lozke.utils.config.VersionedSmartYamlConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;

public class AgorianRifts extends JavaPlugin {

    private static AgorianRifts plugin;
    private static SmartYamlConfiguration gearData;

    private BossBarHandler bossBarHandler;
    private ActionBarMessenger actionBarMessenger;

    @Override
    public void onEnable() {
        plugin = this;

        gearData = defaultSettingsLoad("geardata.yml");

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

        //Turn this into the same setup as commands
        //Turn this into a Factory
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new CoreDamageListener(), this);
        pm.registerEvents(new MenuClickListener(), this);

        //Migrate this to a Factory
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(this.getName(), new BossBarCommand());
            commandMap.register(this.getName(), new TestMenuCommand());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        bossBarHandler = new BossBarHandler(this);
        actionBarMessenger = new ActionBarMessenger();

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

    public static AgorianRifts getPluginInstance() {
        return plugin;
    }

    public static FileConfiguration getGearData() {
        return gearData;
    }

    public BossBarHandler getBossBarHandler() {
        return bossBarHandler;
    }

    public ActionBarMessenger getActionBarMessenger() {
        return actionBarMessenger;
    }
}
