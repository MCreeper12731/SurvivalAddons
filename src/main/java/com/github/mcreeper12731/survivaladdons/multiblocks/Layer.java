package com.github.mcreeper12731.survivaladdons.multiblocks;

import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;

public class Layer {

    private final Multiblock multiblock;
    @Getter
    private final Material[][] structure;
    private final int yLevel;

    public Layer(Multiblock multiblock, int length, int width, int yLevel) {
        this.multiblock = multiblock;
        this.structure = new Material[width][length];
        this.yLevel = yLevel;
    }

    public Layer fill(Material material) {
        for (Material[] materials : structure) {
            Arrays.fill(materials, material);
        }
        return this;
    }

    public Layer set(int x, int z, Material material) {
        structure[z][x] = material;
        return this;
    }

    public Layer setCorners(Material material) {
        int[] xValues = {0, 0, structure[0].length - 1, structure[0].length - 1};
        int[] zValues = {0, structure.length, 0, structure.length - 1};
        for (int i = 0; i < 4; i++) {
            structure[zValues[i]][xValues[i]] = material;
        }
        return this;
    }

    public Multiblock build() {
        return multiblock.addLayer(this, yLevel);
    }
}
