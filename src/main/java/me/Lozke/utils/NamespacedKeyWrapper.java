package me.Lozke.utils;

import me.Lozke.api.IARNamespacedKeyWrapper;
import me.Lozke.api.INamespacedKeyWrapper;
import me.Lozke.data.ARNamespacedKey;
import me.Lozke.data.ListDataType;
import me.Lozke.data.MapDataType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

public class NamespacedKeyWrapper implements INamespacedKeyWrapper, IARNamespacedKeyWrapper {

    private final MapDataType MAP_DATA_TYPE = new MapDataType();
    private final ListDataType LIST_DATA_TYPE = new ListDataType();

    private ItemStack item;
    private ItemMeta itemMeta;

    public NamespacedKeyWrapper(ItemStack item) {
        this.item = item;
        this.itemMeta = (item.getItemMeta() == null) ? Bukkit.getServer().getItemFactory().getItemMeta(item.getType()) : item.getItemMeta();
    }

    public NamespacedKeyWrapper addKey(NamespacedKey namespacedKey, PersistentDataType dataType, Object key) {
        itemMeta.getPersistentDataContainer().set(namespacedKey, dataType, key);
        item.setItemMeta(itemMeta);
        return this;
    }

    public NamespacedKeyWrapper removeKey(NamespacedKey namespacedKey) {
        itemMeta.getPersistentDataContainer().remove(namespacedKey);
        item.setItemMeta(itemMeta);
        return this;
    }

    public boolean hasKey(NamespacedKey namespacedKey, PersistentDataType dataType) {
        return itemMeta.getPersistentDataContainer().has(namespacedKey, dataType);
    }

    public Object get(NamespacedKey namespacedKey, PersistentDataType dataType) {
        return itemMeta.getPersistentDataContainer().get(namespacedKey, dataType);
    }

    public String getString(NamespacedKey namespacedKey, String def) {
        return itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
    }
    public String getString(NamespacedKey namespacedKey) {
        return getString(namespacedKey, null);
    }

    public byte getByte(NamespacedKey namespacedKey, Byte def) {
        Object val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.BYTE);
        return (val == null) ? def : (byte) val;
    }
    public byte getByte(NamespacedKey namespacedKey) {
        return getByte(namespacedKey, null);
    }
    public byte[] getByteArray(NamespacedKey namespacedKey, byte[] def) {
        Object val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.BYTE_ARRAY);
        return (val == null) ? def : (byte[]) val;
    }
    public byte[] getByteArray(NamespacedKey namespacedKey) {
        return getByteArray(namespacedKey, null);
    }

    public int getInt(NamespacedKey namespacedKey, Integer def) {
        Integer val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER);
        return (val == null) ? def : val.intValue();
    }
    public int getInt(NamespacedKey namespacedKey) {
        return getInt(namespacedKey, null);
    }
    public int[] getIntArray(NamespacedKey namespacedKey, int[] def) {
        Object val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER_ARRAY);
        return (val == null) ? def : (int[]) val;
    }
    public int[] getIntArray(NamespacedKey namespacedKey) {
        return getIntArray(namespacedKey, null);
    }

    public double getDouble(NamespacedKey namespacedKey, Double def) {
        Object val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.DOUBLE);
        return (val == null) ? def : (double) val;
    }
    public double getDouble(NamespacedKey namespacedKey) {
        return getDouble(namespacedKey, null);
    }

    public float getFloat(NamespacedKey namespacedKey, Float def) {
        Object val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.FLOAT);
        return (val == null) ? def : (float) val;
    }
    public float getFloat(NamespacedKey namespacedKey) {
        return getFloat(namespacedKey, null);
    }

    public long getLong(NamespacedKey namespacedKey, Long def) {
        Object val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.LONG);
        return (val == null) ? def : (long) val;
    }
    public long getLong(NamespacedKey namespacedKey) {
        return getLong(namespacedKey, null);
    }
    public long[] getLongArray(NamespacedKey namespacedKey, long[] def) {
        Object val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.LONG_ARRAY);
        return val == null ? def : (long[]) val;
    }
    public long[] getLongArray(NamespacedKey namespacedKey) {
        return getLongArray(namespacedKey, null);
    }

    public short getShort(NamespacedKey namespacedKey, Short def) {
        Object val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.SHORT);
        return (val == null) ? def : (short) val;
    }
    public short getShort(NamespacedKey namespacedKey) {
        return getShort(namespacedKey, null);
    }

    public PersistentDataContainer getTagContainer(NamespacedKey namespacedKey, PersistentDataContainer def) {
        PersistentDataContainer val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.TAG_CONTAINER);
        return (val == null) ? def : val;
    }
    public PersistentDataContainer getTagContainer(NamespacedKey namespacedKey) {
        return getTagContainer(namespacedKey, null);
    }
    public PersistentDataContainer[] getTagContainerArray(NamespacedKey namespacedKey, PersistentDataContainer[] def) {
        PersistentDataContainer[] val = itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.TAG_CONTAINER_ARRAY);
        return (val == null) ? def : val;
    }
    public PersistentDataContainer[] getTagContainerArray(NamespacedKey namespacedKey) {
        return getTagContainerArray(namespacedKey, null);
    }

    public Map getMap(NamespacedKey namespacedKey, Map def) {
        Map val = itemMeta.getPersistentDataContainer().get(namespacedKey, MAP_DATA_TYPE);
        return (val == null) ? def : val;
    }
    public Map getMap(NamespacedKey namespacedKey) {
        return getMap(namespacedKey, null);
    }

    public List getList(NamespacedKey namespacedKey, List def) {
        List val = itemMeta.getPersistentDataContainer().get(namespacedKey, LIST_DATA_TYPE);
        return (val == null) ? def : val;
    }
    public List getList(NamespacedKey namespacedKey) {
        return getList(namespacedKey, null);
    }

    /*
     * AR NamespacedKey Hook
     */

    public NamespacedKeyWrapper addKey(ARNamespacedKey namespacedKey, Object key) {
        return addKey(namespacedKey.getNamespacedKey(), namespacedKey.getDataType(), key);
    }
    public NamespacedKeyWrapper addKey(ARNamespacedKey namespacedKey) {
        return addKey(namespacedKey, namespacedKey.getDefaultKey());
    }

    public NamespacedKeyWrapper removeKey(ARNamespacedKey namespacedKey) {
        return removeKey(namespacedKey.getNamespacedKey());
    }

    public boolean hasKey(ARNamespacedKey namespacedKey) {
        return hasKey(namespacedKey.getNamespacedKey(), namespacedKey.getDataType());
    }

    public Object get(ARNamespacedKey namespacedKey) {
        return get(namespacedKey.getNamespacedKey(), namespacedKey.getDataType());
    }

    public String getString(ARNamespacedKey namespacedKey) {
        return getString(namespacedKey.getNamespacedKey(), (String) namespacedKey.getDefaultKey());
    }

    public byte getByte(ARNamespacedKey namespacedKey) {
        return getByte(namespacedKey.getNamespacedKey(), (byte) namespacedKey.getDefaultKey());
    }
    public byte[] getByteArray(ARNamespacedKey namespacedKey) {
        return getByteArray(namespacedKey.getNamespacedKey(), (byte[]) namespacedKey.getDefaultKey());
    }

    public int getInt(ARNamespacedKey namespacedKey) {
        return getInt(namespacedKey.getNamespacedKey(), (Integer) namespacedKey.getDefaultKey());
    }
    public int[] getIntArray(ARNamespacedKey namespacedKey) {
        return getIntArray(namespacedKey.getNamespacedKey(), (int[]) namespacedKey.getDefaultKey());
    }

    public double getDouble(ARNamespacedKey namespacedKey) {
        return getDouble(namespacedKey.getNamespacedKey(), (double) namespacedKey.getDefaultKey());
    }

    public float getFloat(ARNamespacedKey namespacedKey) {
        return getFloat(namespacedKey.getNamespacedKey(), (float) namespacedKey.getDefaultKey());
    }

    public long getLong(ARNamespacedKey namespacedKey) {
        return getLong(namespacedKey.getNamespacedKey(), (long) namespacedKey.getDefaultKey());
    }
    public long[] getLongArray(ARNamespacedKey namespacedKey) {
        return getLongArray(namespacedKey.getNamespacedKey(), (long[]) namespacedKey.getDefaultKey());
    }

    public short getShort(ARNamespacedKey namespacedKey) {
        return getShort(namespacedKey.getNamespacedKey(), (short) namespacedKey.getDefaultKey());
    }

    public PersistentDataContainer getTagContainer(ARNamespacedKey namespacedKey) {
        return getTagContainer(namespacedKey.getNamespacedKey(), (PersistentDataContainer) namespacedKey.getDefaultKey());
    }
    public PersistentDataContainer[] getTagContainerArray(ARNamespacedKey namespacedKey) {
        return getTagContainerArray(namespacedKey.getNamespacedKey(), (PersistentDataContainer[]) namespacedKey.getDefaultKey());
    }

    public Map getMap(ARNamespacedKey namespacedKey) {
        return getMap(namespacedKey.getNamespacedKey(), (Map) namespacedKey.getDefaultKey());
    }

    public List getList(ARNamespacedKey namespacedKey) {
        return getList(namespacedKey.getNamespacedKey(), (List) namespacedKey.getDefaultKey());
    }
}