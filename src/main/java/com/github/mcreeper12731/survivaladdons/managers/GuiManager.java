package com.github.mcreeper12731.survivaladdons.managers;

import com.github.mcreeper12731.survivaladdons.Main;
import com.github.mcreeper12731.survivaladdons.util.Buttons;
import com.github.mcreeper12731.survivaladdons.util.Constants;
import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.SpiGUI;
import com.samjakob.spigui.buttons.SGButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GuiManager {

    private final SpiGUI spiGUI;

    public GuiManager(Main plugin) {
        this.spiGUI = new SpiGUI(plugin);
        spiGUI.setEnableAutomaticPagination(false);
    }

    private SGMenu getCraftingInventory() {

        SGMenu craftingInventory = spiGUI.create("Advanced Crafting", 6);
        craftingInventory.setBlockDefaultInteractions(false);

        for (int i = 0; i < craftingInventory.getPageSize(); i++) craftingInventory.setButton(i, Buttons.getVoidButton(Material.GRAY_STAINED_GLASS_PANE));

        craftingInventory.setButton(Constants.CRAFTING_RESULT, new SGButton(new ItemStack(Material.AIR)).withListener(listener -> {}));
        for (int emptySlot : Constants.CRAFTING_GRID) {
            craftingInventory.setButton(emptySlot, new SGButton(new ItemStack(Material.AIR)).withListener(listener -> {}));
        }

        return craftingInventory;
    }

    public void openCraftingInventory(Player player) {
        player.openInventory(getCraftingInventory().getInventory());
    }

}
