package org.soraworld.zombies.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ServerUtils {

    private static String PLAIN_HEAD = "[Zombies] ";
    private static String COLOR_HEAD = ChatColor.GREEN + PLAIN_HEAD + ChatColor.RESET;
    private static String DEBUG_HEAD = ChatColor.GREEN + PLAIN_HEAD + ChatColor.RED + "[DEBUG] " + ChatColor.RESET;

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

    public static void debug(boolean debug, String message) {
        if (debug) {
            Bukkit.getConsoleSender().sendMessage(DEBUG_HEAD + message);
        }
    }

    public static void setHead(String head) {
        if (head != null && !head.isEmpty()) {
            PLAIN_HEAD = head;
            COLOR_HEAD = ChatColor.GREEN + PLAIN_HEAD + ChatColor.RESET;
            DEBUG_HEAD = ChatColor.GREEN + PLAIN_HEAD + ChatColor.RED + "[DEBUG] " + ChatColor.RESET;
        }
    }

}
