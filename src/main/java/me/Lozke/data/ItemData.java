package me.Lozke.data;

import me.Lozke.RetardRealms;
import org.bukkit.NamespacedKey;

public class ItemData {
    final private static RetardRealms plugin = RetardRealms.getPluginInstance();
    final public static NamespacedKey realItem = new NamespacedKey(plugin, "a-retarded-item");
    final public static NamespacedKey HP = new NamespacedKey(plugin, "retarded-HP");
    final public static NamespacedKey DMG = new NamespacedKey(plugin, "retarded-DMG");
    final public static NamespacedKey spawnerWandToggle = new NamespacedKey(plugin, "spawnerwand-toggle"); //Let's convert this to a boolean data type!
}
