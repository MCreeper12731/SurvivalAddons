package com.github.mcreeper12731.survivaladdons.listeners;

import com.github.mcreeper12731.survivaladdons.util.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        //if (event.getPlayer().hasPlayedBefore()) return;

        event.getPlayer().sendMessage(Color.color("&6Welcome to MCreeper's server. The server is mostly vanilla, with some " +
                "Vanilla+ additions using plugins. To view all the additions, read the wiki on " +
                "https://github.com/MCreeper12731/SurvivalAddons/wiki"));

    }

}
