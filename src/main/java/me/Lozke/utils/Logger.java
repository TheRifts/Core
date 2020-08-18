/*
 * Created by Noah Pritchard on 4/11/2020
 */
package me.Lozke.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class Logger {

    private static final String PREFIX_REGEX = "[%plugin_name%] ";
    private static final String DEFAULT_PREFIX = "[Agorian Rifts] ";
    private static final java.util.logging.Logger logger = Bukkit.getServer().getLogger();

    private static String getPrefix(Plugin plugin) {
        return PREFIX_REGEX.replace("%plugin_name%", plugin.getName());
    }

    public static void log(Level level, Plugin plugin, String message) {
        logger.log(level, getPrefix(plugin) + message);
    }
    public static void log(Level level, String message) {
        logger.log(level, DEFAULT_PREFIX + message);
    }

    public static void log(Plugin plugin, String message) {
        logger.info(getPrefix(plugin) + message);
    }
    public static void log(String message) {
        logger.info(DEFAULT_PREFIX + message);
    }

    public static void broadcast(Plugin plugin, String message) {
        Bukkit.broadcastMessage(getPrefix(plugin) + message);
    }
    public static void broadcast(String message) {
        Bukkit.broadcastMessage(DEFAULT_PREFIX + message);
    }

    public static void warn(String message) {
        logger.log(Level.WARNING, DEFAULT_PREFIX + message);
    }
    public static void warn(Plugin plugin, String message) {
        logger.log(Level.WARNING, getPrefix(plugin) + message);
    }

    public static void error(String message) {
        logger.log(Level.SEVERE, DEFAULT_PREFIX + message);
    }
    public static void error(Plugin plugin, String message) {
        logger.log(Level.SEVERE, getPrefix(plugin) + message);
    }
}
