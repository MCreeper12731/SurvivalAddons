package com.github.mcreeper12731.survivaladdons.guis;

import com.github.MCreeper12731.CreeperItems;
import com.github.MCreeper12731.Interaction;
import com.github.mcreeper12731.survivaladdons.Main;
import com.github.mcreeper12731.survivaladdons.managers.ItemsManager;
import com.github.mcreeper12731.survivaladdons.models.CraftingRecipe;
import com.github.mcreeper12731.survivaladdons.util.Constants;
import com.github.mcreeper12731.survivaladdons.util.ItemUtil;
import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedCraftingGui implements Listener {

    private final Main plugin;
    private final CreeperItems creeperItems;
    private final ItemsManager itemsManager;
    private final Map<CraftingRecipe, ItemStack> recipes = new HashMap<>();

    public AdvancedCraftingGui(Main plugin, CreeperItems creeperItems, ItemsManager itemsManager) {
        this.plugin = plugin;
        this.creeperItems = creeperItems;
        this.itemsManager = itemsManager;
        initRecipes();
    }

    @EventHandler
    public void onClickOnCraftingInventory(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory clickedInventory = event.getClickedInventory();

        if (!isCraftingInventory(clickedInventory, event.getView().getTitle())) return;

        int slot = event.getSlot();

        validateRecipe(clickedInventory);

        if (slot == Constants.CRAFTING_RESULT) {

            ItemStack clickedItem = clickedInventory.getItem(slot);

            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            if (clickedItem.getType() == Material.WHITE_STAINED_GLASS_PANE) {
                event.setCancelled(true);
                return;
            }

            ItemStack itemOnCursor = player.getItemOnCursor().clone();
            itemOnCursor.setAmount(1);
            ItemStack copiedClickedItem = clickedItem.clone();
            copiedClickedItem.setAmount(1);

            if (itemOnCursor.equals(copiedClickedItem)) {
                event.setCancelled(true);
                player.getItemOnCursor().setAmount(player.getItemOnCursor().getAmount() + clickedItem.getAmount());
            }

            for (int i : Constants.CRAFTING_GRID) {

                ItemStack currItem = clickedInventory.getItem(i);
                if (currItem == null) continue;
                if (currItem.getType().name().contains("BUCKET")) currItem = new ItemStack(Material.BUCKET);
                currItem.setAmount(currItem.getAmount() - 1);

                clickedInventory.setItem(i, currItem);
            }
        }
    }

    @EventHandler
    public void onDragOnCraftingInventory(InventoryDragEvent event) {

        Inventory clickedInventory = event.getInventory();

        if (!isCraftingInventory(clickedInventory, event.getView().getTitle())) return;

        validateRecipe(clickedInventory);

    }

    @EventHandler
    public void onCloseCraftingInventory(InventoryCloseEvent event) {
        Inventory closedInventory = event.getInventory();

        if (!isCraftingInventory(closedInventory, event.getView().getTitle())) return;

        for (int i : Constants.CRAFTING_GRID) {

            ItemStack currentItem = closedInventory.getItem(i);
            if (currentItem == null) continue;
            Location location = event.getPlayer().getLocation();
            location.getWorld().dropItem(location, currentItem);

        }
    }

    private void validateRecipe(Inventory clickedInventory) {

        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack result = recipes.get(new CraftingRecipe(getContents(clickedInventory)));

                if (result == null) result = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);

                clickedInventory.setItem(Constants.CRAFTING_RESULT, result);
            }
        }.runTaskLater(plugin, 1L);

    }

    private List<ItemStack> getContents(Inventory inventory) {

        List<ItemStack> returnList = new ArrayList<>();

        for (int slot : Constants.CRAFTING_GRID) {
            ItemStack item = inventory.getItem(slot);
            if (item == null) item = new ItemStack(Material.AIR);
            item = item.clone();
            if (item.getType() != Material.AIR) item.setAmount(1);
            returnList.add(item);
        }


        return returnList;

    }

    private void initRecipes() {
        addRecipe(new CraftingRecipe()
                        .addItem(0, new ItemStack(Material.BREAD))
                        .addItem(3, new ItemStack(Material.COOKED_BEEF))
                        .addItem(6, new ItemStack(Material.BREAD)),
                creeperItems.getItem("burger").getItem()
        );
        addRecipe(new CraftingRecipe()
                        .addItem(1, new ItemStack(Material.BREAD))
                        .addItem(4, new ItemStack(Material.COOKED_BEEF))
                        .addItem(7, new ItemStack(Material.BREAD)),
                creeperItems.getItem("burger").getItem()
        );
        addRecipe(new CraftingRecipe()
                        .addItem(2, new ItemStack(Material.BREAD))
                        .addItem(5, new ItemStack(Material.COOKED_BEEF))
                        .addItem(8, new ItemStack(Material.BREAD)),
                creeperItems.getItem("burger").getItem()
        );
        addRecipe(new CraftingRecipe()
                        .addItem(0, new ItemStack(Material.BREAD))
                        .addItem(3, new ItemStack(Material.COOKED_PORKCHOP))
                        .addItem(6, new ItemStack(Material.BREAD)),
                creeperItems.getItem("burger").getItem()
        );
        addRecipe(new CraftingRecipe()
                        .addItem(1, new ItemStack(Material.BREAD))
                        .addItem(4, new ItemStack(Material.COOKED_PORKCHOP))
                        .addItem(7, new ItemStack(Material.BREAD)),
                creeperItems.getItem("burger").getItem()
        );
        addRecipe(new CraftingRecipe()
                        .addItem(2, new ItemStack(Material.BREAD))
                        .addItem(5, new ItemStack(Material.COOKED_PORKCHOP))
                        .addItem(8, new ItemStack(Material.BREAD)),
                creeperItems.getItem("burger").getItem()
        );
        addRecipe(new CraftingRecipe()
                        .addItem(0, Material.IRON_BLOCK)
                        .addItem(1, Material.IRON_BLOCK)
                        .addItem(2, Material.IRON_BLOCK)
                        .addItem(4, Material.STICK)
                        .addItem(7, Material.STICK),
                creeperItems.getItem("iron_hammer").getItem()
        );
        addRecipe(new CraftingRecipe()
                .addItem(0, Material.GOLD_BLOCK)
                .addItem(1, Material.GOLD_BLOCK)
                .addItem(2, Material.GOLD_BLOCK)
                .addItem(4, Material.STICK)
                .addItem(7, Material.STICK),
                creeperItems.getItem("golden_hammer").getItem()
        );
        addRecipe(new CraftingRecipe()
                        .addItem(0, Material.DIAMOND_BLOCK)
                        .addItem(1, Material.DIAMOND_BLOCK)
                        .addItem(2, Material.DIAMOND_BLOCK)
                        .addItem(4, Material.STICK)
                        .addItem(7, Material.STICK),
                creeperItems.getItem("diamond_hammer").getItem()
        );
        addRecipe(new CraftingRecipe()
                        .addItem(0, Material.NETHERITE_INGOT)
                        .addItem(1, Material.NETHERITE_INGOT)
                        .addItem(2, Material.NETHERITE_INGOT)
                        .addItem(4, Material.STICK)
                        .addItem(7, Material.STICK),
                creeperItems.getItem("netherite_hammer").getItem()
        );
        addRecipe(new CraftingRecipe()
                        .addItem(0, Material.IRON_BLOCK)
                        .addItem(1, Material.IRON_BLOCK)
                        .addItem(3, Material.IRON_BLOCK)
                        .addItem(4, Material.STICK)
                        .addItem(7, Material.STICK),
                creeperItems.getItem("iron_lumber_axe").getItem());
        addRecipe(new CraftingRecipe()
                        .addItem(0, Material.GOLD_BLOCK)
                        .addItem(1, Material.GOLD_BLOCK)
                        .addItem(3, Material.GOLD_BLOCK)
                        .addItem(4, Material.STICK)
                        .addItem(7, Material.STICK),
                creeperItems.getItem("golden_lumber_axe").getItem());
        addRecipe(new CraftingRecipe()
                        .addItem(0, Material.DIAMOND_BLOCK)
                        .addItem(1, Material.DIAMOND_BLOCK)
                        .addItem(3, Material.DIAMOND_BLOCK)
                        .addItem(4, Material.STICK)
                        .addItem(7, Material.STICK),
                creeperItems.getItem("diamond_lumber_axe").getItem());
        addRecipe(new CraftingRecipe()
                        .addItem(0, Material.NETHERITE_INGOT)
                        .addItem(1, Material.NETHERITE_INGOT)
                        .addItem(3, Material.NETHERITE_INGOT)
                        .addItem(4, Material.STICK)
                        .addItem(7, Material.STICK),
                creeperItems.getItem("netherite_lumber_axe").getItem());
        addRecipe(new CraftingRecipe()
                        .addItem(0, Material.DIAMOND)
                        .addItem(1, Material.EMERALD)
                        .addItem(2, Material.DIAMOND)
                        .addItem(3, Material.ELYTRA)
                        .addItem(4, Material.SADDLE)
                        .addItem(5, Material.ELYTRA)
                        .addItem(6, Material.DIAMOND)
                        .addItem(7, Material.NETHERITE_INGOT)
                        .addItem(8, Material.DIAMOND),
                creeperItems.getItem("pegasus").getItem()
        );
        addRecipe(new CraftingRecipe(Material.MILK_BUCKET)
                        .addItem(0, Material.EGG)
                        .addItem(1, Material.EGG)
                        .addItem(2, Material.EGG)
                        .addItem(3, Material.WHEAT)
                        .addItem(4, Material.WATER_BUCKET)
                        .addItem(5, Material.WHEAT),
                ItemUtil.addCMD(new ItemBuilder(Material.GOLDEN_APPLE)
                        .name("&fPancakes")
                        .lore("&7Just like a golden apple!")
                        .build(), 1234567)
        );
        addRecipe(new CraftingRecipe()
                        .addItem(0, Material.COPPER_INGOT)
                        .addItem(3, Material.STICK)
                        .addItem(6, Material.FEATHER),
                new ItemStack(Material.ARROW, 2)
        );
        addRecipe(new CraftingRecipe()
                        .addItem(1, Material.COPPER_INGOT)
                        .addItem(4, Material.STICK)
                        .addItem(7, Material.FEATHER),
                new ItemStack(Material.ARROW, 2)
        );
        addRecipe(new CraftingRecipe()
                        .addItem(2, Material.COPPER_INGOT)
                        .addItem(5, Material.STICK)
                        .addItem(8, Material.FEATHER),
                new ItemStack(Material.ARROW, 2)
        );
        addRecipe(new CraftingRecipe()
                .addItem(0, Material.COCOA_BEANS)
                .addItem(1, Material.SUGAR),
                creeperItems.getItem("chocolate_bar").getItem());
    }

    private void addRecipe(CraftingRecipe recipe, ItemStack result) {
        recipes.put(recipe, result);
    }

    private boolean isCraftingInventory(Inventory inventory, String title) {

        if (!title.equals("Advanced Crafting")) return false;

        if (inventory == null || inventory.getHolder() == null || !(inventory.getHolder() instanceof SGMenu clickedGui)) return false;

        if (!clickedGui.getOwner().equals(plugin)) return false;

        return true;
    }

}
