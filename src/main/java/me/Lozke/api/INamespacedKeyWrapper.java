package me.Lozke.api;

import me.Lozke.data.PersistentDataType.ListDataType;
import me.Lozke.data.PersistentDataType.MapDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

public interface INamespacedKeyWrapper {

    MapDataType MAP_DATA_TYPE = new MapDataType();
    ListDataType LIST_DATA_TYPE = new ListDataType();

    ItemStack item = null;
    ItemMeta itemMeta = null;

    INamespacedKeyWrapper addKey(NamespacedKey namespacedKey, PersistentDataType dataType, Object key);

    INamespacedKeyWrapper removeKey(NamespacedKey namespacedKey);

    boolean hasKey(NamespacedKey namespacedKey, PersistentDataType dataType);

    Object get(NamespacedKey namespacedKey, PersistentDataType dataType);

    String getString(NamespacedKey namespacedKey, String def);
    String getString(NamespacedKey namespacedKey);

    byte getByte(NamespacedKey namespacedKey, Byte def);
    byte getByte(NamespacedKey namespacedKey);
    byte[] getByteArray(NamespacedKey namespacedKey, byte[] def);
    byte[] getByteArray(NamespacedKey namespacedKey);

    int getInt(NamespacedKey namespacedKey, Integer def);
    int getInt(NamespacedKey namespacedKey);
    int[] getIntArray(NamespacedKey namespacedKey, int[] def);
    int[] getIntArray(NamespacedKey namespacedKey);

    double getDouble(NamespacedKey namespacedKey, Double def);
    double getDouble(NamespacedKey namespacedKey);

    float getFloat(NamespacedKey namespacedKey, Float def);
    float getFloat(NamespacedKey namespacedKey);

    long getLong(NamespacedKey namespacedKey, Long def);
    long getLong(NamespacedKey namespacedKey);
    long[] getLongArray(NamespacedKey namespacedKey, long[] def);
    long[] getLongArray(NamespacedKey namespacedKey);

    short getShort(NamespacedKey namespacedKey, Short def);
    short getShort(NamespacedKey namespacedKey);

    PersistentDataContainer getTagContainer(NamespacedKey namespacedKey, PersistentDataContainer def);
    PersistentDataContainer getTagContainer(NamespacedKey namespacedKey);
    PersistentDataContainer[] getTagContainerArray(NamespacedKey namespacedKey, PersistentDataContainer[] def);
    PersistentDataContainer[] getTagContainerArray(NamespacedKey namespacedKey);

    Map getMap(NamespacedKey namespacedKey, Map def);
    Map getMap(NamespacedKey namespacedKey);

    List getList(NamespacedKey namespacedKey, List def);
    List getList(NamespacedKey namespacedKey);

    Boolean getBoolean(NamespacedKey namespacedKey);
}
