package com.github.mcreeper12731.survivaladdons.managers;

import com.github.MCreeper12731.CItem;
import com.github.MCreeper12731.CreeperItems;
import com.github.MCreeper12731.Interaction;
import com.github.mcreeper12731.survivaladdons.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.Vector;

public class ItemsManager implements Listener {

    private final Main main;
    private final CreeperItems creeperItems;

    private final ItemStack woodenHammer;
    private final ItemStack stoneHammer;
    private final ItemStack ironHammer;
    private final ItemStack goldHammer;
    private final ItemStack diamondHammer;
    private final ItemStack netheriteHammer;

    public ItemsManager(Main main, CreeperItems creeperItems) {
        this.main = main;
        this.creeperItems = creeperItems;
        Bukkit.getPluginManager().registerEvents(this, main);
        woodenHammer = null;
        stoneHammer = null;
        ironHammer = null;
        goldHammer = null;
        diamondHammer = creeperItems.create(new ItemStack(Material.DIAMOND_PICKAXE), "diamond_hammer")
                .withBlockBreakListener(event -> {
                    Player player = event.getPlayer();

                    Vector direction = player.getEyeLocation().getDirection();
                    Location minedLocation = event.getBlock().getLocation().clone();

                    if (direction.getY() > 0.71 || direction.getY() < -0.71) {
                        for (int i = -1; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                minedLocation.clone().add(i, 0, j).getBlock().breakNaturally();
                            }
                        }
                        return;
                    }

                    if (Math.abs(direction.getX()) > Math.abs(direction.getZ())) {
                        for (int i = -1; i < 2; i++) {
                            for (int j = -1; j < 2; j++) {
                                minedLocation.clone().add(0, i, j).getBlock().breakNaturally();
                            }
                        }
                        return;
                    }

                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            minedLocation.clone().add(i, j, 0).getBlock().breakNaturally();
                        }
                    }


                })
                .getItem();
        netheriteHammer = null;
    }

    public ItemStack getHammer(Material material) {

        return switch (material) {
            case IRON_INGOT -> ironHammer;
            case DIAMOND -> diamondHammer;
            default -> new ItemStack(Material.AIR);
        };

    }
}
