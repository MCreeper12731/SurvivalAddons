package com.github.mcreeper12731.survivaladdons.guis;

import com.github.MCreeper12731.CreeperItems;
import com.github.MCreeper12731.Interaction;
import com.github.mcreeper12731.survivaladdons.Main;
import com.github.mcreeper12731.survivaladdons.managers.ItemsManager;
import com.github.mcreeper12731.survivaladdons.models.CraftingRecipe;
import com.github.mcreeper12731.survivaladdons.util.Constants;
import com.github.mcreeper12731.survivaladdons.util.ItemUtil;
import com.samjakob.spigui.SGMenu;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedCraftingGui implements Listener {

    private final Main plugin;
    private final CreeperItems itemsApi;
    private final ItemsManager itemsManager;
    private final Map<CraftingRecipe, ItemStack> recipes = new HashMap<>();

    public AdvancedCraftingGui(Main plugin, CreeperItems itemsApi, ItemsManager itemsManager) {
        this.plugin = plugin;
        this.itemsApi = itemsApi;
        this.itemsManager = itemsManager;
        initRecipes();
    }

    @EventHandler
    public void onOpenCraftingInventory(InventoryClickEvent event) {

        Inventory clickedInventory = event.getClickedInventory();

        if (!event.getView().getTitle().equals("Advanced Crafting")) return;

        if (clickedInventory == null || clickedInventory.getHolder() == null || !(clickedInventory.getHolder() instanceof SGMenu clickedGui)) return;

        if (!clickedGui.getOwner().equals(plugin)) return;

        int slot = event.getSlot();

        validateRecipe(clickedInventory);

        if (slot == Constants.CRAFTING_RESULT) {

            if (clickedInventory.getItem(slot) == null || clickedInventory.getItem(slot).getType() == Material.AIR) return;

            for (int i : Constants.CRAFTING_GRID) {

                ItemStack currItem = clickedInventory.getItem(i);
                if (currItem == null) continue;
                currItem.setAmount(currItem.getAmount() - 1);

                clickedInventory.setItem(i, currItem);
            }
        }
    }

    private void validateRecipe(Inventory clickedInventory) {

        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack result = recipes.get(new CraftingRecipe(getContents(clickedInventory)));

                if (result == null) result = new ItemStack(Material.AIR);

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
        addRecipe(new CraftingRecipe(Material.GOLD_NUGGET)
                        .addItem(4, new ItemStack(Material.DIAMOND_SWORD)),
                itemsApi.create(
                        new ItemStack(Material.DIAMOND_SWORD),
                        "lucky-sword"
                ).withClickListener(Interaction.RIGHT_CLICK_ANY, event -> {
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                }).getItem()
        );
        addRecipe(new CraftingRecipe()
                        .addItem(0, new ItemStack(Material.BREAD))
                        .addItem(1, new ItemStack(Material.COOKED_BEEF)),
                ItemUtil.addCMD(new ItemStack(Material.BREAD), 1234567)
        );
        addRecipe(new CraftingRecipe()
                .addItem(0, Material.DIAMOND)
                .addItem(1, Material.DIAMOND)
                .addItem(2, Material.DIAMOND)
                .addItem(4, Material.STICK)
                .addItem(7, Material.STICK),
                itemsManager.getHammer(Material.DIAMOND)
        );
    }

    private void addRecipe(CraftingRecipe recipe, ItemStack result) {
        recipes.put(recipe, result);
    }

}
