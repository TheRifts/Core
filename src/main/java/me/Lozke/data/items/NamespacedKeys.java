package me.Lozke.data.items;

import me.Lozke.FallingAutism;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class NamespacedKeys {
    final private static FallingAutism plugin = FallingAutism.getPluginInstance();
    final public static NamespacedKey realItem = new NamespacedKey(plugin, "a-autistic-item");
    final public static NamespacedKey healthPoints = new NamespacedKey(plugin, "autistic-health-points");
    final public static NamespacedKey damage = new NamespacedKey(plugin, "autistic-damage");
    final public static NamespacedKey spawnerWandToggle = new NamespacedKey(plugin, "spawner-wand-toggle"); //Let's convert this to a boolean data type!
    final public static NamespacedKey attributes = new NamespacedKey(plugin, "autistic-attributes");
    final public static NamespacedKey tier = new NamespacedKey(plugin, "tier");
    final public static NamespacedKey rarity = new NamespacedKey(plugin, "rarity");
    final public static PersistentDataType<byte[], Map> MAP_PERSISTENT_DATA_TYPE = new MapDataType();
    final public static NamespacedKey hpRegen = new NamespacedKey(plugin, "hp-regen");
    final public static NamespacedKey energyRegen = new NamespacedKey(plugin, "energy-regen");
    final public static NamespacedKey canOrb = new NamespacedKey(plugin, "can-orb"); //Let's convert this to a boolean data type!
}
