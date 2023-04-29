package com.github.mcreeper12731.survivaladdons.models;

import com.github.MCreeper12731.citem.CItemMineListener;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.util.Vector;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class LumberAxeListener implements CItemMineListener {

    @Override
    public void execute(BlockBreakEvent event) {

        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItemInMainHand();

        Queue<Location> blocksToBreak = new PriorityQueue<>(Comparator.comparing(Location::toString));
        blocksToBreak.add(event.getBlock().getLocation());

        while (!blocksToBreak.isEmpty()) {

            Location currLocation = blocksToBreak.poll();
            currLocation.getBlock().breakNaturally(item);

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    Location neighborLocation = currLocation.clone().add(i, 1, j);
                    if (blocksToBreak.contains(neighborLocation)) continue;
                    if (neighborLocation.getBlock().getType().name().contains("LOG")) blocksToBreak.add(neighborLocation);
                }
            }

        }

    }
}
