package me.Lozke.commands;

import me.Lozke.utils.ItemMenu.icons.CloseIcon;
import me.Lozke.utils.ItemMenu.icons.MenuIcon;
import me.Lozke.utils.ItemMenu.icons.OpenMenuIcon;
import me.Lozke.utils.ItemMenu.icons.ReturnIcon;
import me.Lozke.utils.ItemMenu.menus.ItemMenu;
import me.Lozke.utils.Items;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class TestMenuCommand extends Command {

    public TestMenuCommand() {
        super("testmenu");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        ItemMenu testMenu1 = new ItemMenu(InventoryType.CHEST, 3, "test-1");
        testMenu1.addDisplayItem(new ReturnIcon(Items.formatItem(new ItemStack(Material.YELLOW_STAINED_GLASS_PANE), "&eReturn Icon (Useless)")));

        MenuIcon[] menu2_icons = new MenuIcon[]{new MenuIcon(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), "Regular Item"), new ReturnIcon(Items.formatItem(new ItemStack(Material.YELLOW_STAINED_GLASS_PANE), "&eReturn Icon (Useless)")), new CloseIcon(Items.formatItem(new ItemStack(Material.RED_STAINED_GLASS_PANE), "&cClose Item"))};
        ItemMenu testMenu2 = new ItemMenu(InventoryType.CHEST, 1, "test-2", menu2_icons);

        testMenu2.setParent(testMenu1);
        testMenu1.setParent(testMenu2);

        MenuIcon[] menu3_icons = new MenuIcon[]{new OpenMenuIcon(Items.formatItem(new ItemStack(Material.PURPLE_STAINED_GLASS_PANE), "Open test-1"), testMenu1), new OpenMenuIcon(Items.formatItem(new ItemStack(Material.PURPLE_STAINED_GLASS_PANE), "Return to test-2"), testMenu2)};
        ItemMenu testMenu3 = new ItemMenu(InventoryType.HOPPER, "test-3", menu3_icons);

        testMenu2.addDisplayItem(new OpenMenuIcon(Items.formatItem(new ItemStack(Material.PURPLE_STAINED_GLASS_PANE), "Open test-3"), testMenu3));

        testMenu2.openMenu((Player) commandSender);
        return true;
    }
}
