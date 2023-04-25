package com.github.mcreeper12731.survivaladdons.util;

import org.bukkit.ChatColor;

public class Color {

    public static String color(String text) {
        return ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String strip(String text) {
        return ChatColor.stripColor(text);
    }

}
