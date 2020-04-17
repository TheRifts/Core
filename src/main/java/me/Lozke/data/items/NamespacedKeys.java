package me.Lozke.data.items;

import me.Lozke.FallingAutism;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class NamespacedKeys {
    final private static FallingAutism plugin = FallingAutism.getPluginInstance();
    final public static NamespacedKey realItem = new NamespacedKey(plugin, "a-autistic-item");
    final public static NamespacedKey HP = new NamespacedKey(plugin, "autistic-HP");
    final public static NamespacedKey DMG = new NamespacedKey(plugin, "autistic-DMG");
    final public static NamespacedKey spawnerWandToggle = new NamespacedKey(plugin, "spawnerwand-toggle"); //Let's convert this to a boolean data type!
    final public static NamespacedKey attributes = new NamespacedKey(plugin, "autistic-Attributes");
    final public static NamespacedKey tier = new NamespacedKey(plugin, "tier");
    final public static NamespacedKey rarity = new NamespacedKey(plugin, "rarity");
    final public static PersistentDataType<byte[], Map> MAP_PERSISTENT_DATA_TYPE = new MapDataType();
}
