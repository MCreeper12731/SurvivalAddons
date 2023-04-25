package com.github.mcreeper12731.survivaladdons.multiblocks;

import com.github.mcreeper12731.survivaladdons.Main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class MagicAltar implements Listener {

    private final Main plugin;
    Set<MagicAltarRunnable> registeredAltars = new HashSet<>();
    Map<ItemStack, ItemStack> recipes = new HashMap<>();

    public MagicAltar(Main plugin) {
        this.plugin = plugin;
        recipes.put(new ItemStack(Material.DIRT), new ItemStack(Material.DIAMOND));
        recipes.put(new ItemStack(Material.OAK_SAPLING), new ItemStack(Material.SPRUCE_SAPLING));
        recipes.put(new ItemStack(Material.SPRUCE_SAPLING), new ItemStack(Material.BIRCH_SAPLING));
        recipes.put(new ItemStack(Material.BIRCH_SAPLING), new ItemStack(Material.JUNGLE_SAPLING));
        recipes.put(new ItemStack(Material.JUNGLE_SAPLING), new ItemStack(Material.ACACIA_SAPLING));
        recipes.put(new ItemStack(Material.ACACIA_SAPLING), new ItemStack(Material.DARK_OAK_SAPLING));
        recipes.put(new ItemStack(Material.DARK_OAK_SAPLING), new ItemStack(Material.OAK_SAPLING));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null || event.getItem().getType() != Material.STICK) return;
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.GOLD_BLOCK) return;
        Location clickedLocation = event.getClickedBlock().getLocation();
        for (int i = -1; i < 1; i++) {
            for (int j = -1; j < 1; j++) {
                if (i == 0 && j == 0) continue;
                Location clonedLocation = clickedLocation.clone().add(i, 0, j);
                if (clonedLocation.getBlock().getType() != Material.DIAMOND_BLOCK) return;
            }
        }
        MagicAltarRunnable mar = new MagicAltarRunnable(clickedLocation);
        registeredAltars.add(mar);
        mar.start();
        player.sendMessage("Successfully added altar!");
    }

    class MagicAltarRunnable extends BukkitRunnable {

        private final Location center;
        private boolean transforming = false;

        public MagicAltarRunnable(Location center) {
            this.center = center;
        }

        @Override
        public void run() {
            List<Entity> entities = List.copyOf(center.getWorld().getNearbyEntities(center.clone().add(0.5, 1, 0.5), 1, 0.5,1));
            if (transforming) return;
            entities.forEach(entity -> {
                if (entity.getType() != EntityType.DROPPED_ITEM) return;
                ItemStack item = ((Item) entity).getItemStack();
                ItemStack result = recipes.get(item);
                if (result == null) return;
                Bukkit.broadcastMessage("Starting transformation");
                entity.remove();
                transforming = true;
                new ItemRunnable(item, result, center.clone().add(0.5, 1, 0.5)).start();
            });
        }

        public void start() {
            this.runTaskTimer(plugin, 0L, 20L);
        }

        class ItemRunnable extends BukkitRunnable {

            private final ItemStack item;
            private final ItemStack result;
            private final Location itemLocation;
            private int timeLeft = 5 * 10;

            public ItemRunnable(ItemStack item, ItemStack result, Location itemLocation) {
                this.item = item;
                this.result = result;
                this.itemLocation = itemLocation;
            }

            @Override
            public void run() {
                timeLeft--;

                if (timeLeft <= -20) {
                    Bukkit.broadcastMessage("Can transform again!");
                    this.cancel();
                    transforming = false;
                    return;
                }

                if (timeLeft == 0) {
                    Item droppedItem = itemLocation.getWorld().dropItem(itemLocation.clone().add(0, 1.5, 0), result);
                    droppedItem.setGravity(false);
                    droppedItem.setVelocity(new Vector(0, 0, 0));
                    return;
                }

                Particle.DustOptions dustOptions = new Particle.DustOptions(timeLeft > 0 ? Color.fromRGB(255, 0, 0) : Color.fromRGB(0, 255, 0), 1.0F);
                center.getWorld().spawnParticle(Particle.REDSTONE, itemLocation.clone().add(Math.cos(timeLeft) * 2, 0, Math.sin(timeLeft) * 2), 5, dustOptions);

            }

            public void start() {
                this.runTaskTimer(plugin, 0L, 2L);
            }
        }
    }

}
