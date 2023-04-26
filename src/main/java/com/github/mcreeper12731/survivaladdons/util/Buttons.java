package com.github.mcreeper12731.survivaladdons.util;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Buttons {

    public static SGButton getVoidButton(Material material) {
        return getVoidButton(new ItemBuilder(material).name("").build());
    }
    public static SGButton getVoidButton(ItemStack item) {
        return new SGButton(item).withListener(event -> event.setCancelled(true));
    }

}
