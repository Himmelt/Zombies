package org.soraworld.zombies.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.soraworld.zombies.config.Config;

import java.util.ArrayList;

public class CommandZombie extends IICommand {

    public CommandZombie(String name, final Config config) {
        super(name);
        addSub(new IICommand("save") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                config.save();
                sender.sendMessage("saved!");
                return true;
            }
        });
        addSub(new IICommand("reload") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                config.load();
                sender.sendMessage("reloaded!");
                return true;
            }
        });
        addSub(new IICommand("allow") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (sender instanceof Player) {
                    if (args.isEmpty()) {
                        config.allow(((Player) sender).getWorld().getName());
                    } else {
                        config.allow(args.get(0));
                    }
                    return true;
                }
                if (!args.isEmpty()) {
                    config.allow(args.get(0));
                    return true;
                }
                sender.sendMessage("no world name!");
                return false;
            }
        });
        addSub(new IICommand("disallow") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (sender instanceof Player) {
                    if (args.isEmpty()) {
                        config.disallow(((Player) sender).getWorld().getName());
                    } else {
                        config.disallow(args.get(0));
                    }
                    return true;
                }
                if (!args.isEmpty()) {
                    config.disallow(args.get(0));
                    return true;
                }
                sender.sendMessage("no world name!");
                return false;
            }
        });
        addSub(new IICommand("speed") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (args.isEmpty()) {
                    sender.sendMessage("Speed effect level is " + config.minSpeed() + "-" + config.maxSpeed());
                } else if (args.get(0).matches("[0-9]+-[0-9]+")) {
                    String[] ss = args.get(0).split("-");
                    config.minSpeed(Integer.valueOf(ss[0]));
                    config.maxSpeed(Integer.valueOf(ss[1]));
                } else {
                    sender.sendMessage("/zombie speed <min>-<max>");
                }
                return true;
            }
        });
        addSub(new IICommand("health") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (args.isEmpty()) {
                    sender.sendMessage("Health is between " + config.minHealth() + "-" + config.maxHealth());
                } else if (args.get(0).matches("[0-9]+-[0-9]+")) {
                    String[] ss = args.get(0).split("-");
                    config.minHealth(Integer.valueOf(ss[0]));
                    config.maxHealth(Integer.valueOf(ss[1]));
                } else {
                    sender.sendMessage("/zombie health <min>-<max>");
                }
                return true;
            }
        });
    }

}
