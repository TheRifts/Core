package me.Lozke.data.items;

import me.Lozke.FallingAutism;
import org.bukkit.NamespacedKey;

public class NamespacedKeys {
    final private static FallingAutism plugin = FallingAutism.getPluginInstance();
    final public static NamespacedKey realItem = new NamespacedKey(plugin, "a-autistic-item");
    final public static NamespacedKey HP = new NamespacedKey(plugin, "autistic-HP");
    final public static NamespacedKey DMG = new NamespacedKey(plugin, "autistic-DMG");
    final public static NamespacedKey spawnerWandToggle = new NamespacedKey(plugin, "spawnerwand-toggle"); //Let's convert this to a boolean data type!
}
