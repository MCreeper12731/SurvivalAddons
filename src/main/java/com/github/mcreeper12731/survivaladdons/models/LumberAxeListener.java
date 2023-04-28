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

        Damageable meta = (Damageable) item.getItemMeta();

        Queue<Block> blocksToBreak = new PriorityQueue<>(Comparator.comparing(b -> b.getLocation().toString()));
        blocksToBreak.add(event.getBlock());

        while (!blocksToBreak.isEmpty()) {

            meta.setDamage(meta.getDamage() + 1);

            Block currBlock = blocksToBreak.poll();
            Location blockLocation = currBlock.getLocation().clone().add(0, 1, 0);
            currBlock.breakNaturally(item);

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    Location currLocation = blockLocation.clone().add(i, 0, j);
                    if (currLocation.getBlock().getType().name().contains("LOG")) blocksToBreak.add(currLocation.getBlock());
                }
            }

        }

        item.setItemMeta(meta);

    }
}
