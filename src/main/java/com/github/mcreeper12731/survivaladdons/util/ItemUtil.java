package com.github.mcreeper12731.survivaladdons.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

    public static ItemStack addCMD(ItemStack item, int data) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(data);
        item.setItemMeta(meta);
        return item;
    }

}
