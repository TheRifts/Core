package me.Lozke.data;

import me.Lozke.RetardRealms;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public enum Tier {
    T1("WHITE", Material.WHITE_CONCRETE,"LEATHER", "WOODEN", "&7"), //Wood/Leather
    T2("LIME", Material.LIME_CONCRETE,"CHAINMAIL", "STONE", "&2"), //Stone/Chain
    T3("LIGHT_BLUE", Material.LIGHT_BLUE_CONCRETE,"IRON", "IRON","&b"), //Steel
    T4("PURPLE", Material.PURPLE_CONCRETE,"DIAMOND","DIAMOND","&5"), //Ivory
    T5("YELLOW", Material.YELLOW_CONCRETE,"GOLDEN","GOLDEN", "&e"); //Heavenly
    //T6 Demonic

    public static Tier[] types = Tier.values();

    private final String materialColor;
    private final Material material;
    private final String armourMaterial;
    private final String weaponMaterial;
    private final String colorCode;

    Tier(String materialColor, Material material, String armourMaterial, String weaponMaterial, String colorCode) {
        this.materialColor = materialColor;
        this.material = material;
        this.armourMaterial = armourMaterial;
        this.weaponMaterial = weaponMaterial;
        this.colorCode = colorCode;
    }

    public String getMaterialColor() {
        return materialColor;
    }

    public Material getMaterial() {
        return material;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getArmourMaterial() {
        return armourMaterial;
    }

    public String getWeaponMaterial() {
        return weaponMaterial;
    }

    //Everything below this needs to be made into an Item class
    private ItemStack createItem(String itemType) {
        ItemStack item = null;
        ItemMeta itemMeta = null;
        PersistentDataContainer dataContainer = null;
        switch (itemType) {
            case "_HELMET":
            case "_CHESTPLATE":
            case "_LEGGINGS":
            case "_BOOTS":
                item = new ItemStack(Material.valueOf(armourMaterial + itemType));
                itemMeta = item.getItemMeta();
                dataContainer = itemMeta.getPersistentDataContainer();
                dataContainer.set(ItemData.HP, PersistentDataType.INTEGER, new Random().nextInt(RetardRealms.getGearData().getInt("Helmet.LO")));
                break;
            case "_SWORD":
            case "_AXE":
            case "_SHOVEL":
            case "_HOE":
                item = new ItemStack(Material.valueOf(weaponMaterial + itemType));
                itemMeta = item.getItemMeta();
                dataContainer = itemMeta.getPersistentDataContainer();
                dataContainer.set(ItemData.DMG, PersistentDataType.INTEGER, 5000);
                break;
        }
        dataContainer.set(ItemData.realItem, PersistentDataType.STRING, "Certified RetardRealms™ Item");
        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack getHelmet() {
        return createItem("_HELMET");
    }

    public ItemStack getChestplate() {
        return createItem("_CHESTPLATE");
    }

    public ItemStack getLeggings() {
        return createItem("_LEGGINGS");
    }

    public ItemStack getBoots() {
        return createItem("_BOOTS");
    }

    //lol this would be the perfect place to return a Set<ItemStack>... just saying...
    public ItemStack[] getSet() {
        return new ItemStack[]{getHelmet(), getChestplate(), getLeggings(), getBoots()};
    }

    public ItemStack getWeapon(String type) {
        return createItem("_" + type.toUpperCase());
    }
}