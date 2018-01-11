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
                sender.sendMessage("config saved!");
                return true;
            }
        });
        addSub(new IICommand("reload") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                config.load();
                sender.sendMessage("config reloaded!");
                return true;
            }
        });
        addSub(new IICommand("allow") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (sender instanceof Player) {
                    if (args.isEmpty()) {
                        config.allow(((Player) sender).getWorld().getName());
                        sender.sendMessage("World [" + ((Player) sender).getWorld().getName() + "] is allowed!");
                    } else {
                        config.allow(args.get(0));
                        sender.sendMessage("World [" + args.get(0) + "] is allowed!");
                    }
                    return true;
                }
                if (!args.isEmpty()) {
                    config.allow(args.get(0));
                    sender.sendMessage("World [" + args.get(0) + "] is allowed!");
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
                        sender.sendMessage("World [" + ((Player) sender).getWorld().getName() + "] is disallowed!");
                    } else {
                        config.disallow(args.get(0));
                        sender.sendMessage("World [" + args.get(0) + "] is disallowed!");
                    }
                    return true;
                }
                if (!args.isEmpty()) {
                    config.disallow(args.get(0));
                    sender.sendMessage("World [" + args.get(0) + "] is disallowed!");
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
                    sender.sendMessage("Speed effect level is between " + config.minSpeed() + "-" + config.maxSpeed());
                } else if (args.get(0).matches("[0-9]+-[0-9]+")) {
                    String[] ss = args.get(0).split("-");
                    config.minSpeed(Integer.valueOf(ss[0]));
                    config.maxSpeed(Integer.valueOf(ss[1]));
                    sender.sendMessage("Speed effect level is set to " + config.minSpeed() + "-" + config.maxSpeed());
                } else {
                    sender.sendMessage("/zombie speed [min-max]");
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
                    sender.sendMessage("Health is set to " + config.minHealth() + "-" + config.maxHealth());
                } else {
                    sender.sendMessage("/zombie health [min-max]");
                }
                return true;
            }
        });
        addSub(new IICommand("radius") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (args.isEmpty()) {
                    sender.sendMessage("Spawn radius is between " + config.minSpawnRadius() + "-" + config.maxSpawnRadius());
                } else if (args.get(0).matches("[0-9]+-[0-9]+")) {
                    String[] ss = args.get(0).split("-");
                    config.minSpawnRadius(Integer.valueOf(ss[0]));
                    config.maxSpawnRadius(Integer.valueOf(ss[1]));
                    sender.sendMessage("Spawn radius is set to " + config.minSpawnRadius() + "-" + config.maxSpawnRadius());
                } else {
                    sender.sendMessage("/zombie radius [min-max]");
                }
                return true;
            }
        });
        addSub(new IICommand("refresh") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (args.isEmpty()) {
                    sender.sendMessage("Refresh time is " + config.refresh() + " s");
                } else if (args.get(0).matches("[0-9]+")) {
                    config.refresh(Integer.valueOf(args.get(0)));
                    sender.sendMessage("Refresh time is set to " + config.refresh());
                } else {
                    sender.sendMessage("/zombie refresh [time]");
                }
                return true;
            }
        });
    }

}
