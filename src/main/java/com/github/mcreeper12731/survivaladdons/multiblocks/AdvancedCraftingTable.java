package com.github.mcreeper12731.survivaladdons.multiblocks;

import com.github.mcreeper12731.survivaladdons.managers.GuiManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AdvancedCraftingTable implements Listener {

    private final GuiManager guiManager;

    public AdvancedCraftingTable(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onOpenCraftingTable(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.CRAFTING_TABLE) return;

        Location checkLocation = clickedBlock.getLocation().clone().add(0, -1, 0);

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (checkLocation.clone().add(i, 0, j).getBlock().getType() != Material.IRON_BLOCK) return;
            }
        }

        event.setCancelled(true);

        guiManager.openCraftingInventory(event.getPlayer());

    }

}
