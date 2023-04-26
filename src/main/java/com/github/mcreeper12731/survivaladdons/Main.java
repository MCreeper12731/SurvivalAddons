package com.github.mcreeper12731.survivaladdons;

import com.github.MCreeper12731.CreeperItems;
import com.github.mcreeper12731.survivaladdons.guis.AdvancedCraftingGui;
import com.github.mcreeper12731.survivaladdons.listeners.HorseRideListener;
import com.github.mcreeper12731.survivaladdons.listeners.LadderPlaceListener;
import com.github.mcreeper12731.survivaladdons.listeners.ShulkerOpenListener;
import com.github.mcreeper12731.survivaladdons.listeners.Testing;
import com.github.mcreeper12731.survivaladdons.managers.GuiManager;
import com.github.mcreeper12731.survivaladdons.managers.ItemsManager;
import com.github.mcreeper12731.survivaladdons.multiblocks.AdvancedCraftingTable;
import com.github.mcreeper12731.survivaladdons.multiblocks.MagicAltar;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {

    private GuiManager guiManager;
    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig());
        registerCommands();
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable(this);

        CreeperItems creeperItems = new CreeperItems(this, "survival_addons");
        guiManager = new GuiManager(this);
        ItemsManager itemsManager = new ItemsManager(this, creeperItems);

        registerListeners(
                new MagicAltar(this),
                new LadderPlaceListener(),
                new ShulkerOpenListener(),
                new HorseRideListener(),
                new AdvancedCraftingGui(this, creeperItems, itemsManager),
                new AdvancedCraftingTable(guiManager),
                new Testing());
    }

    private void registerListeners(Listener... listeners) {
        PluginManager manager = Bukkit.getPluginManager();
        Arrays.asList(listeners).forEach(listener -> manager.registerEvents(listener, this));
    }

    private void registerCommands() {



    }
}
