/*
 * Created by Noah Pritchard on 4/11/2020
 */
package me.Lozke.utils;

import org.bukkit.Bukkit;

public class Logger {

    public static final String prefix = "[Agorian Rifts] ";
    public static final java.util.logging.Logger logger = Bukkit.getServer().getLogger();

    public static void log(String message) {
        logger.info(prefix + message);
    }

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(prefix + message);
    }

    //TODO add warn/error support for logger
}
