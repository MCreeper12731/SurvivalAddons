package com.github.mcreeper12731.survivaladdons.managers;

import com.github.mcreeper12731.survivaladdons.Main;
import com.samjakob.spigui.SpiGUI;

public class GuiManager {

    private final Main plugin;
    private final SpiGUI spiGUI;

    public GuiManager(Main plugin) {
        this.plugin = plugin;
        this.spiGUI = new SpiGUI(plugin);
    }

    //methods for GUIs

}
