package com.github.mcreeper12731.survivaladdons.multiblocks;

import com.github.mcreeper12731.survivaladdons.Main;
import com.github.mcreeper12731.survivaladdons.models.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public abstract class Multiblock implements Listener {

    private final Position relativeCenter;
    private final Position absoluteCenter;
    /**
     * Array size: Y x Z x X
     * structure[y] returns y-th layer
     * structure[y][z][x] returns a specific Material at x, y, z coordinates
     *
     * Values are stored as an offset from center (e.g., if center is (1, 1, 1), the Material at structure[0][0][0]
     * is a Vector of (-1, -1, -1) from center
     */
    private final Material[][][] structure;

    //passed as x, y, z
    public Multiblock(Main plugin,
                      Position relativeCenter,
                      Position absoluteCenter,
                      int length, int height, int width) {
        this.relativeCenter = relativeCenter;
        this.absoluteCenter = absoluteCenter;
        this.structure = new Material[height][width][length];
        Bukkit.getPluginManager().registerEvents(this, plugin);
        addStructure();
    }

    public abstract void addStructure();

    public Layer addLayer(int y) {
        return new Layer(this, structure[0].length, structure[0][0].length, y);
    }

    public Multiblock addLayer(Layer layer, int y) {
        Material[][] layerStructure = layer.getStructure();
        for (int i = 0; i < structure[0].length; i++) {
            /*
            for (int j = 0; j < structure[0][0].length; j++) {
                structure[y][i][j] = layerStructure[i][j];
            }
             */
            System.arraycopy(layerStructure[i], 0, structure[y][i], 0, structure[0][0].length);
        }
        return this;
    }

    @EventHandler
    public void onActivation(PlayerInteractEvent event) {

        //Check for initial conditions
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != structure[(int)relativeCenter.getY()][(int)relativeCenter.getZ()][(int)relativeCenter.getX()]);

        Location centerLocation = absoluteCenter.getLocation(event.getClickedBlock().getWorld());

        for (int y = 0; y < structure.length; y++) {
            for (int z = 0; z < structure[0].length; z++) {
                for (int x = 0; x < structure[0][0].length; x++) {
                    Vector offsetVector = new Vector(x - relativeCenter.getX(), y - relativeCenter.getY(), z - relativeCenter.getZ());
                    Location offsetLocation = centerLocation.clone().add(offsetVector);

                    if (offsetLocation.getBlock().getType() != structure[offsetVector.getBlockY()][offsetVector.getBlockZ()][offsetVector.getBlockX()]) return;
                }
            }
        }

        event.getPlayer().sendMessage("This is a valid " + this.getClass().getName() + " multiblock!");
    }
}
