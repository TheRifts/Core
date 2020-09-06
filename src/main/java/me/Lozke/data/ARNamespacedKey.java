package me.Lozke.data;

import me.Lozke.AgorianRifts;
import me.Lozke.data.PersistentDataType.BooleanDataType;
import me.Lozke.data.PersistentDataType.ListDataType;
import me.Lozke.data.PersistentDataType.MapDataType;
import me.Lozke.data.PersistentDataType.UUIDDataType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public enum ARNamespacedKey {
    UUID(new NamespacedKey(getPlugin(), "uuid"), getUUIDDataType(), java.util.UUID.randomUUID()),
    SPAWNER_WAND_TOGGLE(new NamespacedKey(getPlugin(), "spawner-wand-toggle"), getBooleanDataType(), true),
    REAL_ITEM(new NamespacedKey(getPlugin(), "a-real-item"), getBooleanDataType(), true),
    TIER(new NamespacedKey(getPlugin(), "tier"), PersistentDataType.STRING, "tier"),
    RARITY(new NamespacedKey(getPlugin(), "rarity"), PersistentDataType.STRING, "rarity"),
    HEALTH_POINTS(new NamespacedKey(getPlugin(), "health-points"), PersistentDataType.INTEGER, 0),
    DMG_LO(new NamespacedKey(getPlugin(), "dmg-lo"), PersistentDataType.INTEGER, 0),
    DMG_HI(new NamespacedKey(getPlugin(), "dmg-hi"), PersistentDataType.INTEGER, 0),
    HP_REGEN(new NamespacedKey(getPlugin(), "hp-regen"), PersistentDataType.INTEGER, 0),
    ENERGY_REGEN(new NamespacedKey(getPlugin(), "energy-regen"), PersistentDataType.INTEGER, 0),
    DEFENSE(new NamespacedKey(getPlugin(), "defense-value"), PersistentDataType.INTEGER, 0),
    GEM_WORTH(new NamespacedKey(getPlugin(), "gem-amount"), PersistentDataType.INTEGER, 0),
    MAX_GEM_WORTH(new NamespacedKey(getPlugin(), "max-gem-amount"), PersistentDataType.INTEGER, 0),
    HELD_ITEMS(new NamespacedKey(getPlugin(), "held-items"), getMapDataType(), new HashMap<>(0)),
    CAN_ORB(new NamespacedKey(getPlugin(), "can-orb"), getBooleanDataType(), true),
    ATTRIBUTES(new NamespacedKey(getPlugin(), "item-attributes"), getMapDataType(), new HashMap<>(0)),
    DURABILITY(new NamespacedKey(getPlugin(), "durability"), PersistentDataType.INTEGER, 0),
    MAX_DURABILITY(new NamespacedKey(getPlugin(), "max-durability"), PersistentDataType.INTEGER, 0),
    BROKEN(new NamespacedKey(getPlugin(), "is-broken"), getBooleanDataType(), false),
    USED_SCROLLS(new NamespacedKey(getPlugin(), "used-scroll"), getListDataType(), new ArrayList<>()),
    SCROLL_MAX_AMOUNT(new NamespacedKey(getPlugin(), "scroll-max-amount"), PersistentDataType.INTEGER, 0),
    SCROLL_SUCCESS_CHANCE(new NamespacedKey(getPlugin(),"scroll-success-chance"), PersistentDataType.DOUBLE, 0D),
    SCROLL_DESTROY_CHANCE(new NamespacedKey(getPlugin(),"scroll-destroy-chance"), PersistentDataType.DOUBLE, 0D),
    SCROLL_SKIN(new NamespacedKey(getPlugin(), "scroll-skin"), PersistentDataType.STRING, "BLUE"),
    SCROLL_TYPE(new NamespacedKey(getPlugin(), "scroll-type"), PersistentDataType.STRING, "ARMOR");

    public static ARNamespacedKey[] types = ARNamespacedKey.values();

    private NamespacedKey namespacedKey;
    private PersistentDataType dataType;
    private Object defaultKey;

    ARNamespacedKey(NamespacedKey namespacedKey, PersistentDataType dataType, Object defaultKey) {
        this.namespacedKey = namespacedKey;
        this.dataType = dataType;
        this.defaultKey = defaultKey;
    }

    public NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }

    public PersistentDataType getDataType() {
        return dataType;
    }

    public Object getDefaultKey() {
        return defaultKey;
    }

    public static ItemStack addToItem(ItemStack item, NamespacedKey namespacedKey, PersistentDataType dataType, Object key) {
        ItemMeta itemMeta = item.getItemMeta() == null ? Bukkit.getServer().getItemFactory().getItemMeta(item.getType()) : item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, dataType, key);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack addToItem(ItemStack item, ARNamespacedKey namespacedKey, Object key) {
        return addToItem(item, namespacedKey.namespacedKey, namespacedKey.dataType, key);
    }

    public static ItemStack addToItem(ItemStack item, ARNamespacedKey namespacedKey) {
        return addToItem(item, namespacedKey.namespacedKey, namespacedKey.dataType, namespacedKey.defaultKey);
    }

    private static PersistentDataType<byte[], Map> getMapDataType() {
        return new MapDataType();
    }

    private static PersistentDataType<byte[], List> getListDataType() {
        return new ListDataType();
    }

    private static PersistentDataType<byte[], Boolean> getBooleanDataType() {
        return new BooleanDataType();
    }

    private static PersistentDataType<byte[], UUID> getUUIDDataType() {
        return new UUIDDataType();
    }

    private static AgorianRifts getPlugin() {
        return AgorianRifts.getPluginInstance();
    }
}
