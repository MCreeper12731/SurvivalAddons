package com.github.mcreeper12731.survivaladdons;

import com.github.MCreeper12731.CItem;
import com.github.MCreeper12731.CreeperItems;
import com.github.mcreeper12731.survivaladdons.guis.AdvancedCraftingGui;
import com.github.mcreeper12731.survivaladdons.listeners.*;
import com.github.mcreeper12731.survivaladdons.managers.GuiManager;
import com.github.mcreeper12731.survivaladdons.managers.ItemsManager;
import com.github.mcreeper12731.survivaladdons.multiblocks.AdvancedCraftingTable;
import com.github.mcreeper12731.survivaladdons.multiblocks.MagicAltar;
import com.github.mcreeper12731.survivaladdons.util.Color;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandAPIConfig;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {

    private GuiManager guiManager;
    private CreeperItems creeperItems;
    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig());
        registerCommands();
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable(this);

        NamespacedKey mobsKey = new NamespacedKey(this, "mobs");

        creeperItems = new CreeperItems(this, "survival_addons");
        guiManager = new GuiManager(this);
        ItemsManager itemsManager = new ItemsManager(creeperItems, mobsKey);

        registerListeners(
                new MagicAltar(this),
                new LadderPlaceListener(),
                new ShulkerOpenListener(),
                new HorseRideListener(mobsKey),
                new AdvancedCraftingGui(this, creeperItems, itemsManager),
                new AdvancedCraftingTable(guiManager),
                new PlayerJoinListener());
    }

    private void registerListeners(Listener... listeners) {
        PluginManager manager = Bukkit.getPluginManager();
        Arrays.asList(listeners).forEach(listener -> manager.registerEvents(listener, this));
    }

    private void registerCommands() {

        new CommandAPICommand("getitem")
                .withPermission("survivaladdons.getitem")
                .withArguments(new StringArgument("Item ID"))
                .executes((sender, args) -> {
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage("&cOnly players can run this command!");
                        return;
                    }

                    CItem citem = creeperItems.getItem((String) args[0]);
                    if (citem == null) {
                        player.sendMessage(Color.color("&cItem does not exist!"));
                        return;
                    }

                    player.getInventory().addItem(citem.getItem());
                })
                .register();

    }
}
