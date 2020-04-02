package me.Lozke.guis;

import me.Lozke.RetardRealms;
import me.Lozke.handlers.ItemMenu;
import me.Lozke.data.Rarity;
import me.Lozke.data.Tier;
import me.Lozke.utils.Items;
import me.Lozke.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnerEditor implements Listener {

    private me.Lozke.data.MobSpawner spawner;
    private ItemMenu menu;
    private Page currentPage;
    private Page previousPage;

    public SpawnerEditor(me.Lozke.data.MobSpawner spawner) {
        this.spawner = spawner;
        Bukkit.getPluginManager().registerEvents(this, RetardRealms.getPluginInstance());
        setPage(Page.Main);
    }

    private void createIcons() {
        //durrrrrr uhrurhhhhh
    }

    private void setPage(Page page) {
        int slot = 0;
        if (menu == null || !menu.getInventory().getType().equals(page.inventoryType)) {
            menu = new ItemMenu(page.inventoryType, "");
        }
        else {
            menu.clearItems();
        }
        switch (page) {
            case Main:
                currentPage = page;
                menu.addDisplayItem(Items.formatItem(new ItemStack(spawner.getTier().getMaterial()), spawner.getTier().getColorCode() + spawner.getTier() + " &r(" + spawner.getRarity().getColorCode() + spawner.getRarity().getSymbol() + "&r)", new String[]{Text.colorize("&8Left click to edit tier"), Text.colorize("&8Right click to edit rarity")}));
                menu.addDisplayItem(Items.formatItem(new ItemStack(Material.ZOMBIE_HEAD), Text.colorize("&fMob Editor")));
                menu.addDisplayItem(Items.formatItem(new ItemStack(Material.NETHER_STAR), Text.colorize("&fSpawner Mechanics")));
                menu.addDisplayItem(Items.formatItem(new ItemStack(Material.CLOCK), "&fSpawn Timer: " + spawner.getTimeLeft() + "/" + spawner.getSpawnTimer()));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!currentPage.equals(Page.Main) || menu.getInventory().getViewers().size() == 0 || !spawner.getSpawnerStatus()){
                            cancel();
                            return;
                        }
                        menu.updateSlot(3, Items.formatItem(new ItemStack(Material.CLOCK), "&fSpawn Timer: " + spawner.getTimeLeft() + "/" + spawner.getSpawnTimer()));
                    }
                }.runTaskTimer(RetardRealms.getPluginInstance(), 0, 20);
                if (spawner.getSpawnerStatus()) {
                    menu.addDisplayItem(Items.formatItem(new ItemStack(Material.LIME_DYE), "&fSpawner Status: &a&lON"));
                }
                else {
                    menu.addDisplayItem(Items.formatItem(new ItemStack(Material.GRAY_DYE), "&fSpawner Status: &c&lOFF"));
                }
                break;
            case Tier:
                currentPage = page;
                for (Tier tier : Tier.types) {
                    if (spawner.getTier() == tier) {
                        ItemStack itemStack = Items.makeGlow(Items.formatItem(new ItemStack(tier.getMaterial()), Text.colorize(tier.getColorCode() + tier.name())));
                        Items.makeGlow(itemStack);
                        menu.addDisplayItem(itemStack);
                    }
                    else {
                        menu.addDisplayItem(Items.formatItem(new ItemStack(tier.getMaterial()), Text.colorize(tier.getColorCode() + tier.name())));
                    }
                    slot++;
                }
                break;
            case Rarity:
                currentPage = page;
                for (Rarity rarity : Rarity.types) {
                    menu.addDisplayItem(Items.formatItem(rarity.getIcon(), Text.colorize(rarity.getColorCode() + rarity.name())));
                    slot++;
                }
                break;
            case MobEditor:
                currentPage = page;
                menu.addDisplayItem(Items.formatItem(new ItemStack(Material.ZOMBIE_HEAD), Text.colorize("&fType: " + spawner.getMobType()), new String[] {Text.colorize("&8Click to change type")}));
                menu.addDisplayItem(Items.formatItem(new ItemStack(Material.NAME_TAG), Text.colorize("&fName: " + spawner.getMobType()), new String[] {Text.colorize("&8Click to change name")}));
                if (spawner.getEliteStatus()) {
                    ItemStack item = Items.formatItem(new ItemStack(Material.ENDER_EYE), Text.colorize("&fToggle Elite Status"));
                    Items.makeGlow(item);
                    menu.addDisplayItem(item);
                }
                else {
                    menu.addDisplayItem(Items.formatItem(new ItemStack(Material.ENDER_PEARL), Text.colorize("&fToggle Elite Status")));
                }
                menu.setDisplayItem(menu.getInventory().getSize() - 1, Items.formatItem(new ItemStack(Material.RED_CONCRETE), Text.colorize("&cReturn")));
                break;
            case SpawnerEditor:
                currentPage = page;
                menu.setDisplayItem(menu.getInventory().getSize() - 1, Items.formatItem(new ItemStack(Material.RED_CONCRETE), Text.colorize("&cReturn")));
                break;
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().equals(menu.getInventory()))) {
            return;
        }
        event.setCancelled(true);
        int slot = event.getSlot();
        ClickType clickType = event.getClick();
        switch (currentPage) {
            case Main:
                switch (slot) {
                    case 0:
                        if (clickType.isLeftClick()) {
                            previousPage = currentPage;
                            setPage(Page.Tier);
                            break;
                        }
                        if (clickType.isRightClick()) {
                            previousPage = currentPage;
                            setPage(Page.Rarity);
                            break;
                        }
                    case 1:
                        if (clickType.isLeftClick()) {
                            previousPage = currentPage;
                            setPage(Page.MobEditor);
                            break;
                        }
                        break;
                    case 2:
                        previousPage = currentPage;
                        setPage(Page.SpawnerEditor);
                        break;
                    case 4:
                        spawner.toggleSpawnerStatus();
                        setPage(Page.Main);
                        break;
                }
                break;
            case Tier:
                previousPage = currentPage;
                spawner.setTier(Tier.valueOf(Text.decolorize(event.getCurrentItem().getItemMeta().getDisplayName())));
                setPage(Page.Main);
                break;
            case Rarity:
                previousPage = currentPage;
                spawner.setRarity(Rarity.valueOf(Text.decolorize(event.getCurrentItem().getItemMeta().getDisplayName())));
                setPage(Page.Main);
                break;
            case MobEditor:
                switch (slot) {
                    case 2:
                        spawner.toggleEliteStatus();
                        setPage(currentPage);
                        break;
                    case 4:
                        if (clickType.isLeftClick()) {
                            setPage(Page.Main);
                            break;
                        }
                        break;
                }
            case SpawnerEditor:
                switch (slot) {
                    case 4:
                        if (clickType.isLeftClick()) {
                            setPage(Page.Main);
                            break;
                        }
                        break;
                }
        }
    }

    public void openGUI(Player player) {
        player.openInventory(menu.getInventory());
    }

    public Inventory getGui() {
        return menu.getInventory();
    }

    private enum Page {
        Main(InventoryType.HOPPER),
        Tier(InventoryType.HOPPER),
        Rarity(InventoryType.HOPPER),
        MobEditor(InventoryType.HOPPER),
        SpawnerEditor(InventoryType.HOPPER);

        InventoryType inventoryType;

        Page(InventoryType inventoryType) {
            this.inventoryType = inventoryType;
        }

        public InventoryType getInventoryType() {
            return inventoryType;
        }
    }
}
