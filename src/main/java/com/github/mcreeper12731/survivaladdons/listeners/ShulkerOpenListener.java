package com.github.mcreeper12731.survivaladdons.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.HashMap;
import java.util.UUID;

public class ShulkerOpenListener implements Listener {

    private final HashMap<UUID, ItemStack> openShulkerBoxes = new HashMap<>();

    @EventHandler
    public void onShulkerOpen(PlayerInteractEvent event) {

        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        ItemStack item = event.getItem();
        if (item == null || !isShulkerBox(item.getType())) return;

        event.setCancelled(true);

        ShulkerBox shulkerBox = convertToShulker(item);

        Player player = event.getPlayer();
        openShulkerBoxes.put(player.getUniqueId(), item);
        player.openInventory(shulkerBox.getInventory());
        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 0.5f, 0.8f);

    }

    @EventHandler
    public void onShulkerClick(InventoryClickEvent event) {
        if (event.getAction() != InventoryAction.PICKUP_HALF) return;
        ItemStack item = event.getCurrentItem();
        if (item == null || !isShulkerBox(item.getType())) return;

        event.setCancelled(true);

        ShulkerBox shulkerBox = convertToShulker(item);

        if (!(event.getWhoClicked() instanceof Player player)) return;
        player.setItemOnCursor(new ItemStack(Material.AIR));

        openShulkerBoxes.put(player.getUniqueId(), item);
        player.openInventory(shulkerBox.getInventory());
        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 0.5f, 0.8f);
    }

    @EventHandler
    public void onShulkerClose(InventoryCloseEvent event) {

        if (!(event.getPlayer() instanceof Player player)) return;
        
        if (event.getInventory().getType() != InventoryType.SHULKER_BOX) return;
        if (!openShulkerBoxes.containsKey(player.getUniqueId())) return;
        
        ItemStack shulkerItem = openShulkerBoxes.get(player.getUniqueId());
        openShulkerBoxes.remove(player.getUniqueId());

        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, 0.5f, 0.8f);

        BlockStateMeta shulkerMeta = (BlockStateMeta) shulkerItem.getItemMeta();

        ShulkerBox shulkerBox = (ShulkerBox) shulkerMeta.getBlockState();

        Inventory closedInventory = event.getInventory();
        Inventory shulkerInventory = shulkerBox.getInventory();

        for (int i = 0; i < 27; i++) {
            ItemStack item = closedInventory.getItem(i);
            if (item == null) item = new ItemStack(Material.AIR);
            shulkerInventory.setItem(i, item);
        }

        shulkerMeta.setBlockState(shulkerBox);
        shulkerItem.setItemMeta(shulkerMeta);
    }

    private ShulkerBox convertToShulker(ItemStack item) {
        return (ShulkerBox) ((BlockStateMeta) item.getItemMeta()).getBlockState();
    }

    private boolean isShulkerBox(Material material) {
        return switch (material) {
            case LIGHT_GRAY_SHULKER_BOX,
                    BLACK_SHULKER_BOX,
                    BLUE_SHULKER_BOX,
                    BROWN_SHULKER_BOX,
                    CYAN_SHULKER_BOX,
                    GRAY_SHULKER_BOX,
                    GREEN_SHULKER_BOX,
                    LIGHT_BLUE_SHULKER_BOX,
                    LIME_SHULKER_BOX,
                    MAGENTA_SHULKER_BOX,
                    ORANGE_SHULKER_BOX,
                    PINK_SHULKER_BOX,
                    PURPLE_SHULKER_BOX,
                    RED_SHULKER_BOX,
                    WHITE_SHULKER_BOX,
                    YELLOW_SHULKER_BOX,
                    SHULKER_BOX -> true;
            default -> false;
        };
    }

}
