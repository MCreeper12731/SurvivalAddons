package com.github.mcreeper12731.survivaladdons.models;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CraftingRecipe {

    private final List<ItemStack> recipe = new ArrayList<>();

    public CraftingRecipe() {
        this(Material.AIR);
    }

    public CraftingRecipe(Material fillWith) {
        this(new ItemStack(fillWith));
    }

    public CraftingRecipe(ItemStack fillWith) {
        for (int i = 0; i < 9; i++) {
            recipe.add(fillWith);
        }
    }

    public CraftingRecipe(List<ItemStack> items) {
        this.recipe.addAll(items);
    }

    public CraftingRecipe addItem(int slot, ItemStack item) {

        if (slot < 0 || slot > 8 || item == null || item.getType() == Material.AIR) {
            throw new IllegalArgumentException();
        }
        recipe.set(slot, item);

        return this;
    }

    public CraftingRecipe addItem(int slot, Material material) {
        return addItem(slot, new ItemStack(material));
    }

    @Override
    public int hashCode() {
        return this.recipe.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CraftingRecipe craftingRecipe)) return false;
        return this.recipe.equals(craftingRecipe.recipe);
    }
}
