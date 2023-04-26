package com.github.mcreeper12731.survivaladdons.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class Testing implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Vector eyeDirection = event.getPlayer().getEyeLocation().getDirection();
        //event.getPlayer().sendMessage(String.format("%.2f %.2f %.2f", eyeDirection.getX(), eyeDirection.getY(), eyeDirection.getZ()));
    }

}
