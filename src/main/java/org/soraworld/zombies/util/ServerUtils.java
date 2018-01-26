package org.soraworld.zombies.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ServerUtils {

    private static final String PLAIN_HEAD = "[Zombies] ";
    private static final String COLOR_HEAD = ChatColor.GREEN + PLAIN_HEAD + ChatColor.RESET;

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(COLOR_HEAD + message);
    }

    public static void log(String message) {
        System.out.println(PLAIN_HEAD + message);
    }

    public static void console(String message) {
        Bukkit.getConsoleSender().sendMessage(COLOR_HEAD + message);
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(COLOR_HEAD + message);
    }
}
