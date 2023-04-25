package com.github.mcreeper12731.survivaladdons.multiblocks;

import com.github.mcreeper12731.survivaladdons.Main;
import com.github.mcreeper12731.survivaladdons.models.Position;
import org.bukkit.Location;
import org.bukkit.Material;

public class SimpleMultiblock extends Multiblock {

    public SimpleMultiblock(Main plugin, Location absoluteCenter) {
        super(plugin, new Position(1, 1, 1), new Position(absoluteCenter), 3, 3, 3);
    }

    @Override
    public void addStructure() {
        addLayer(0).fill(Material.IRON_BLOCK).build();
        addLayer(1).fill(Material.AIR).set(1, 1, Material.DIAMOND_BLOCK).build();
        addLayer(2).fill(Material.AIR).build();
    }
}
