package com.github.mcreeper12731.survivaladdons.models;

import com.github.MCreeper12731.citem.CItemMineListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.util.Vector;

public class HammerListener implements CItemMineListener {

    @Override
    public void execute(BlockBreakEvent event) {
        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItemInMainHand();
        Vector direction = player.getEyeLocation().getDirection();
        Location minedLocation = event.getBlock().getLocation().clone();

        //Mine blocks based on player direction
        if (direction.getY() > 0.71 || direction.getY() < -0.71) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    minedLocation.clone().add(i, 0, j).getBlock().breakNaturally(item);
                }
            }
            return;
        }

        if (Math.abs(direction.getX()) > Math.abs(direction.getZ())) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    minedLocation.clone().add(0, i, j).getBlock().breakNaturally(item);
                }
            }
            return;
        }

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                minedLocation.clone().add(i, j, 0).getBlock().breakNaturally(item);
            }
        }

    }
}
