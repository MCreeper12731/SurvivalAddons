package com.github.mcreeper12731.survivaladdons.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AetherCreationListener implements Listener {

    @EventHandler
    public void onPlayerPlaceWaterBucket(BlockPlaceEvent event) {

        if (event.getItemInHand().getType() != Material.WATER_BUCKET) return;
        Block clickedBlock = event.getBlockAgainst();
        if (clickedBlock.getType() != Material.GLOWSTONE) return;

        Block waterPosition = event.getBlock();
        //Check for all possible placements

        /*
        GGGG
        G  G
        G  G
        G WG
        GGGG
        */




    }

}
