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
    private Inventory gui;
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
        ItemStack[] items = null;
        switch (page) {
            case Main:
                currentPage = page;
                items = new ItemStack[5];
                items[0] = Items.formatItem(new ItemStack(spawner.getTier().getMaterial()), spawner.getTier().getColorCode() + spawner.getTier() + " &r(" + spawner.getRarity().getColorCode() + spawner.getRarity().getSymbol() + "&r)", new String[]{Text.colorize("&8Left click to edit tier"), Text.colorize("&8Right click to edit rarity")});
                items[1] = Items.formatItem(new ItemStack(Material.ZOMBIE_HEAD), Text.colorize("&f" + spawner.getMobType()));
                if (spawner.getEliteStatus()) {
                    items[2] = Items.formatItem(new ItemStack(Material.ENDER_EYE), Text.colorize("&fToggle Elite Status"));
                    Items.makeGlow(items[2]);
                }
                else {
                    items[2] = Items.formatItem(new ItemStack(Material.ENDER_PEARL), Text.colorize("&fToggle Elite Status"));
                }
                items[3] = Items.formatItem(new ItemStack(Material.CLOCK), "&fSpawn Timer: " + spawner.getTimeLeft() + "/" + spawner.getSpawnTimer());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!currentPage.equals(Page.Main) || gui.getViewers().size() == 0){
                            cancel();
                            return;
                        }
                        gui.setItem(3, Items.formatItem(new ItemStack(Material.CLOCK), "&fSpawn Timer: " + spawner.getTimeLeft() + "/" + spawner.getSpawnTimer()));
                    }
                }.runTaskTimer(RetardRealms.getPluginInstance(), 0, 20);
                if (spawner.getSpawnerStatus()) {
                    items[4] = Items.formatItem(new ItemStack(Material.LIME_DYE), "&fSpawner Status: &a&lON");
                }
                else {
                    items[4] = Items.formatItem(new ItemStack(Material.GRAY_DYE), "&fSpawner Status: &c&lOFF");
                }
                if (gui == null) {
                    gui = new ItemMenu(Page.Main.inventoryType, "", items).getMenu();
                    break;
                }
                for (ItemStack item : items) {
                    gui.setItem(slot, item);
                    slot++;
                }
                break;
            case Tier:
                currentPage = page;
                for (Tier tier : Tier.types) {
                    if (spawner.getTier() == tier) {
                        ItemStack itemStack = Items.formatItem(new ItemStack(tier.getMaterial()), Text.colorize(tier.getColorCode() + tier.name()));
                        Items.makeGlow(itemStack);
                        gui.setItem(slot, itemStack);
                    }
                    else {
                        gui.setItem(slot, Items.formatItem(new ItemStack(tier.getMaterial()), Text.colorize(tier.getColorCode() + tier.name())));
                    }
                    slot++;
                }
                break;
            case Rarity:
                currentPage = page;
                for (Rarity rarity : Rarity.types) {
                    gui.setItem(slot, Items.formatItem(rarity.getIcon(), Text.colorize(rarity.getColorCode() + rarity.name())));
                    slot++;
                }
                break;
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().equals(gui))) {
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
                    case 2:
                        if (clickType.isLeftClick()) {
                            spawner.toggleEliteStatus();
                            setPage(Page.Main);
                            break;
                        }
                        break;
                    case 4:
                        if (clickType.isLeftClick()) {
                            spawner.toggleSpawnerStatus();
                            setPage(Page.Main);
                            break;
                        }
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
        }
    }

    public void openGUI(Player player) {
        player.openInventory(gui);
    }

    public Inventory getGui() {
        return gui;
    }

    private enum Page {
        Main(InventoryType.HOPPER),
        Tier(InventoryType.HOPPER),
        Rarity(InventoryType.HOPPER);

        InventoryType inventoryType;

        Page(InventoryType inventoryType) {
            this.inventoryType = inventoryType;
        }

        public InventoryType getInventoryType() {
            return inventoryType;
        }
    }
}
