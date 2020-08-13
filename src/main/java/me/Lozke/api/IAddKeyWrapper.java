package me.Lozke.api;

import me.Lozke.data.PersistentDataType.BooleanDataType;
import me.Lozke.data.PersistentDataType.ListDataType;
import me.Lozke.data.PersistentDataType.MapDataType;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

public interface IAddKeyWrapper {

    MapDataType MAP_DATA_TYPE = new MapDataType();
    ListDataType LIST_DATA_TYPE = new ListDataType();
    BooleanDataType BOOLEAN_DATA_TYPE = new BooleanDataType();

    IAddKeyWrapper addKey(NamespacedKey namespacedKey, PersistentDataType dataType, Object key);
    IAddKeyWrapper addString(NamespacedKey namespacedKey, String key);
    IAddKeyWrapper addByte(NamespacedKey namespacedKey, byte key);
    IAddKeyWrapper addByteArray(NamespacedKey namespacedKey, byte[] key);
    IAddKeyWrapper addInt(NamespacedKey namespacedKey, int key);
    IAddKeyWrapper addIntArray(NamespacedKey namespacedKey, int[] key);
    IAddKeyWrapper addDouble(NamespacedKey namespacedKey, double key);
    IAddKeyWrapper addFloat(NamespacedKey namespacedKey, float key);
    IAddKeyWrapper addLong(NamespacedKey namespacedKey, long key);
    IAddKeyWrapper addLongArray(NamespacedKey namespacedKey, long[] key);
    IAddKeyWrapper addShort(NamespacedKey namespacedKey, short key);
    IAddKeyWrapper addTagContainer(NamespacedKey namespacedKey, PersistentDataContainer key);
    IAddKeyWrapper addTagContainerArray(NamespacedKey namespacedKey, PersistentDataContainer[] key);
    IAddKeyWrapper addMap(NamespacedKey namespacedKey, Map key);
    IAddKeyWrapper addList(NamespacedKey namespacedKey, List key);
    IAddKeyWrapper addBoolean(NamespacedKey namespacedKey, boolean key);
}
