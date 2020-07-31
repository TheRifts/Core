package me.Lozke.api;

import me.Lozke.data.ARNamespacedKey;
import me.Lozke.utils.NamespacedKeyWrapper;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;
import java.util.Map;

public interface IARNamespacedKeyWrapper {
    NamespacedKeyWrapper addKey(ARNamespacedKey namespacedKey, Object key);
    NamespacedKeyWrapper addKey(ARNamespacedKey namespacedKey);

    NamespacedKeyWrapper removeKey(ARNamespacedKey namespacedKey);

    boolean hasKey(ARNamespacedKey namespacedKey);

    Object get(ARNamespacedKey namespacedKey);

    String getString(ARNamespacedKey namespacedKey);

    byte getByte(ARNamespacedKey namespacedKey);
    byte[] getByteArray(ARNamespacedKey namespacedKey);

    int getInt(ARNamespacedKey namespacedKey);
    int[] getIntArray(ARNamespacedKey namespacedKey);

    double getDouble(ARNamespacedKey namespacedKey);

    float getFloat(ARNamespacedKey namespacedKey);

    long getLong(ARNamespacedKey namespacedKey);
    long[] getLongArray(ARNamespacedKey namespacedKey);

    short getShort(ARNamespacedKey namespacedKey);

    PersistentDataContainer getTagContainer(ARNamespacedKey namespacedKey);
    PersistentDataContainer[] getTagContainerArray(ARNamespacedKey namespacedKey);

    Map getMap(ARNamespacedKey namespacedKey);

    List getList(ARNamespacedKey namespacedKey);
}
