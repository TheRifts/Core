package me.Lozke.utils;

import org.bukkit.ChatColor;

public class Text {
    public static String colorize(String message) {
        return message.replaceAll("&([0-9a-fk-or])", ChatColor.COLOR_CHAR + "$1");
    }

    public static String decolorize(String message) {
        return message.replaceAll(ChatColor.COLOR_CHAR + "([0-9a-fk-or])", "");
    }
}
