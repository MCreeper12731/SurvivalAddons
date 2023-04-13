package com.github.mcreeper12731.survivaladdons;

import com.github.mcreeper12731.survivaladdons.managers.GuiManager;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.plugin.java.JavaPlugin;

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

        guiManager = new GuiManager(this);
    }

    private void registerCommands() {

        //new CommandAPICommand("")...

    }
}
