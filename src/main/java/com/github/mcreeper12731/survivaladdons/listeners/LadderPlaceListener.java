package com.github.mcreeper12731.survivaladdons.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class LadderPlaceListener implements Listener {

    @EventHandler
    public void onLadderPlace(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.LADDER) return;
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.LADDER) return;

        //Find the lowest block under the ladder

        Location temp = clickedBlock.getLocation().clone();
        while (temp.getBlock().getType() == Material.LADDER) {
            temp.add(0, -1, 0);
        }

        //If it's not air return
        if (temp.getBlock().getType() != Material.AIR && temp.getBlock().getType() != Material.CAVE_AIR && temp.getBlock().getType() != Material.VOID_AIR) return;

        event.setCancelled(true);


        Player player = event.getPlayer();
        BlockFace rotation = ((Directional) clickedBlock.getBlockData()).getFacing();

        //Reduce item count and place
        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
            item.setAmount(item.getAmount() - 1);

        //Eye candy
        player.swingMainHand();
        for (Player playSoundPlayer : Bukkit.getOnlinePlayers()) {
            playSoundPlayer.playSound(player.getLocation(), Sound.BLOCK_LADDER_PLACE, 0.8f, 0.8f);
        }

        temp.getBlock().setType(Material.LADDER);
        Directional ladderData = (Directional) temp.getBlock().getBlockData();
        ladderData.setFacing(rotation);
        temp.getBlock().setBlockData(ladderData);

    }

}
