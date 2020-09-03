package me.Lozke.utils;

import me.Lozke.api.IARNamespacedKeyWrapper;
import me.Lozke.api.IAddKeyWrapper;
import me.Lozke.api.INamespacedKeyWrapper;
import me.Lozke.data.ARNamespacedKey;
import me.Lozke.data.PersistentDataType.BooleanDataType;
import me.Lozke.data.PersistentDataType.ListDataType;
import me.Lozke.data.PersistentDataType.MapDataType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

public class NamespacedKeyWrapper implements INamespacedKeyWrapper, IAddKeyWrapper, IARNamespacedKeyWrapper {

    private final MapDataType MAP_DATA_TYPE = new MapDataType();
    private final ListDataType LIST_DATA_TYPE = new ListDataType();
    private final BooleanDataType BOOLEAN_DATA_TYPE  = new BooleanDataType();

    private PersistentDataContainer dataContainer;

    public NamespacedKeyWrapper(PersistentDataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }

    public ItemStack applyDataToItem(ItemStack stack) {
        ItemMeta meta = (stack.getItemMeta() == null) ? Bukkit.getServer().getItemFactory().getItemMeta(stack.getType()) : stack.getItemMeta();
        stack.setItemMeta(meta);
        return stack;
    }

    public NamespacedKeyWrapper addKey(NamespacedKey namespacedKey, PersistentDataType dataType, Object key) {
        dataContainer.set(namespacedKey, dataType, key);
        return this;
    }

    public NamespacedKeyWrapper removeKey(NamespacedKey namespacedKey) {
        dataContainer.remove(namespacedKey);
        return this;
    }

    public boolean hasKey(NamespacedKey namespacedKey, PersistentDataType dataType) {
        return dataContainer.has(namespacedKey, dataType);
    }

    public Object get(NamespacedKey namespacedKey, PersistentDataType dataType) {
        return dataContainer.get(namespacedKey, dataType);
    }

    public NamespacedKeyWrapper addString(NamespacedKey namespacedKey, String key) {
        return addKey(namespacedKey,PersistentDataType.STRING, key);
    }

    public String getString(NamespacedKey namespacedKey, String def) {
        String val = dataContainer.get(namespacedKey, PersistentDataType.STRING);
        return (val == null) ? def : val;
    }
    public String getString(NamespacedKey namespacedKey) {
        return getString(namespacedKey, null);
    }

    public NamespacedKeyWrapper addByte(NamespacedKey namespacedKey, byte key) {
        return addKey(namespacedKey,PersistentDataType.BYTE, key);
    }

    public byte getByte(NamespacedKey namespacedKey, Byte def) {
        Byte val = dataContainer.get(namespacedKey, PersistentDataType.BYTE);
        return (val == null) ? def : val.byteValue();
    }
    public byte getByte(NamespacedKey namespacedKey) {
        return getByte(namespacedKey, null);
    }

    public NamespacedKeyWrapper addByteArray(NamespacedKey namespacedKey, byte[] key) {
        return addKey(namespacedKey,PersistentDataType.BYTE_ARRAY, key);
    }

    public byte[] getByteArray(NamespacedKey namespacedKey, byte[] def) {
        Object val = dataContainer.get(namespacedKey, PersistentDataType.BYTE_ARRAY);
        return (val == null) ? def : (byte[]) val;
    }
    public byte[] getByteArray(NamespacedKey namespacedKey) {
        return getByteArray(namespacedKey, null);
    }

    public NamespacedKeyWrapper addInt(NamespacedKey namespacedKey, int key) {
        return addKey(namespacedKey,PersistentDataType.INTEGER, key);
    }

    public int getInt(NamespacedKey namespacedKey, Integer def) {
        Integer val = dataContainer.get(namespacedKey, PersistentDataType.INTEGER);
        return (val == null) ? def : val.intValue();
    }
    public int getInt(NamespacedKey namespacedKey) {
        return getInt(namespacedKey, null);
    }

    public NamespacedKeyWrapper addIntArray(NamespacedKey namespacedKey, int[] key) {
        return addKey(namespacedKey,PersistentDataType.INTEGER_ARRAY, key);
    }

    public int[] getIntArray(NamespacedKey namespacedKey, int[] def) {
        Object val = dataContainer.get(namespacedKey, PersistentDataType.INTEGER_ARRAY);
        return (val == null) ? def : (int[]) val;
    }
    public int[] getIntArray(NamespacedKey namespacedKey) {
        return getIntArray(namespacedKey, null);
    }

    public NamespacedKeyWrapper addDouble(NamespacedKey namespacedKey, double key) {
        return addKey(namespacedKey,PersistentDataType.DOUBLE, key);
    }

    public double getDouble(NamespacedKey namespacedKey, Double def) {
        Double val = dataContainer.get(namespacedKey, PersistentDataType.DOUBLE);
        return (val == null) ? def : val.doubleValue();
    }
    public double getDouble(NamespacedKey namespacedKey) {
        return getDouble(namespacedKey, null);
    }

    public NamespacedKeyWrapper addFloat(NamespacedKey namespacedKey, float key) {
        return addKey(namespacedKey,PersistentDataType.FLOAT, key);
    }

    public float getFloat(NamespacedKey namespacedKey, Float def) {
        Float val = dataContainer.get(namespacedKey, PersistentDataType.FLOAT);
        return (val == null) ? def : val.floatValue();
    }
    public float getFloat(NamespacedKey namespacedKey) {
        return getFloat(namespacedKey, null);
    }

    public NamespacedKeyWrapper addLong(NamespacedKey namespacedKey, long key) {
        return addKey(namespacedKey,PersistentDataType.LONG, key);
    }

    public long getLong(NamespacedKey namespacedKey, Long def) {
        Long val = dataContainer.get(namespacedKey, PersistentDataType.LONG);
        return (val == null) ? def : val.longValue();
    }
    public long getLong(NamespacedKey namespacedKey) {
        return getLong(namespacedKey, null);
    }

    public NamespacedKeyWrapper addLongArray(NamespacedKey namespacedKey, long[] key) {
        return addKey(namespacedKey,PersistentDataType.LONG_ARRAY, key);
    }

    public long[] getLongArray(NamespacedKey namespacedKey, long[] def) {
        Object val = dataContainer.get(namespacedKey, PersistentDataType.LONG_ARRAY);
        return val == null ? def : (long[]) val;
    }
    public long[] getLongArray(NamespacedKey namespacedKey) {
        return getLongArray(namespacedKey, null);
    }

    public NamespacedKeyWrapper addShort(NamespacedKey namespacedKey, short key) {
        return addKey(namespacedKey,PersistentDataType.SHORT, key);
    }

    public short getShort(NamespacedKey namespacedKey, Short def) {
        Short val = dataContainer.get(namespacedKey, PersistentDataType.SHORT);
        return (val == null) ? def : val.shortValue();
    }
    public short getShort(NamespacedKey namespacedKey) {
        return getShort(namespacedKey, null);
    }

    public NamespacedKeyWrapper addTagContainer(NamespacedKey namespacedKey, PersistentDataContainer key) {
        return addKey(namespacedKey,PersistentDataType.TAG_CONTAINER, key);
    }

    public PersistentDataContainer getTagContainer(NamespacedKey namespacedKey, PersistentDataContainer def) {
        PersistentDataContainer val = dataContainer.get(namespacedKey, PersistentDataType.TAG_CONTAINER);
        return (val == null) ? def : val;
    }
    public PersistentDataContainer getTagContainer(NamespacedKey namespacedKey) {
        return getTagContainer(namespacedKey, null);
    }

    public NamespacedKeyWrapper addTagContainerArray(NamespacedKey namespacedKey, PersistentDataContainer[] key) {
        return addKey(namespacedKey,PersistentDataType.TAG_CONTAINER_ARRAY, key);
    }

    public PersistentDataContainer[] getTagContainerArray(NamespacedKey namespacedKey, PersistentDataContainer[] def) {
        PersistentDataContainer[] val = dataContainer.get(namespacedKey, PersistentDataType.TAG_CONTAINER_ARRAY);
        return (val == null) ? def : val;
    }
    public PersistentDataContainer[] getTagContainerArray(NamespacedKey namespacedKey) {
        return getTagContainerArray(namespacedKey, null);
    }

    public NamespacedKeyWrapper addMap(NamespacedKey namespacedKey, Map key) {
        return addKey(namespacedKey, MAP_DATA_TYPE, key);
    }

    public Map getMap(NamespacedKey namespacedKey, Map def) {
        Map val = dataContainer.get(namespacedKey, MAP_DATA_TYPE);
        return (val == null) ? def : val;
    }
    public Map getMap(NamespacedKey namespacedKey) {
        return getMap(namespacedKey, null);
    }

    public NamespacedKeyWrapper addList(NamespacedKey namespacedKey, List key) {
        return addKey(namespacedKey, LIST_DATA_TYPE, key);
    }

    public List getList(NamespacedKey namespacedKey, List def) {
        List val = dataContainer.get(namespacedKey, LIST_DATA_TYPE);
        return (val == null) ? def : val;
    }
    public List getList(NamespacedKey namespacedKey) {
        return getList(namespacedKey, null);
    }

    public NamespacedKeyWrapper addBoolean(NamespacedKey namespacedKey, boolean key) {
        return addKey(namespacedKey, BOOLEAN_DATA_TYPE, key);
    }

    public boolean getBoolean(NamespacedKey namespacedKey, Boolean def) {
        Boolean val = dataContainer.get(namespacedKey, BOOLEAN_DATA_TYPE);
        return (val == null) ? def : val;
    }

    public boolean getBoolean(NamespacedKey namespacedKey) {
        return getBoolean(namespacedKey,null);
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

    @Override
    public boolean getBoolean(ARNamespacedKey namespacedKey) {
        return getBoolean(namespacedKey.getNamespacedKey(), (boolean) namespacedKey.getDefaultKey());
    }
}