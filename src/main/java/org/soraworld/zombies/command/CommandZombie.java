package org.soraworld.zombies.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.soraworld.zombies.config.Config;
import org.soraworld.zombies.config.LangKeys;
import org.soraworld.zombies.task.SpawnTask;
import org.soraworld.zombies.util.ServerUtils;

import java.util.ArrayList;

public class CommandZombie extends IICommand {

    public CommandZombie(String name, final Plugin plugin, final Config config) {
        super(name);
        addSub(new IICommand("save") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                config.save();
                ServerUtils.send(sender, LangKeys.format("configSaved"));
                return true;
            }
        });
        addSub(new IICommand("reload") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                config.load();
                SpawnTask.runNewTask(plugin, config);
                ServerUtils.send(sender, LangKeys.format("configReloaded"));
                return true;
            }
        });
        addSub(new IICommand("allow") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (sender instanceof Player) {
                    if (args.isEmpty()) {
                        String worldName = ((Player) sender).getWorld().getName();
                        config.allow(worldName);
                        ServerUtils.send(sender, LangKeys.format("worldAllowed", worldName));
                    } else {
                        config.allow(args.get(0));
                        ServerUtils.send(sender, LangKeys.format("worldAllowed", args.get(0)));
                    }
                    return true;
                }
                if (!args.isEmpty()) {
                    config.allow(args.get(0));
                    ServerUtils.send(sender, LangKeys.format("worldAllowed", args.get(0)));
                    return true;
                }
                ServerUtils.send(sender, LangKeys.format("invalidWorldName"));
                return false;
            }
        });
        addSub(new IICommand("disallow") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (sender instanceof Player) {
                    if (args.isEmpty()) {
                        String worldName = ((Player) sender).getWorld().getName();
                        config.disallow(worldName);
                        ServerUtils.send(sender, LangKeys.format("worldDisallowed", worldName));
                    } else {
                        config.disallow(args.get(0));
                        ServerUtils.send(sender, LangKeys.format("worldDisallowed", args.get(0)));
                    }
                    return true;
                }
                if (!args.isEmpty()) {
                    config.disallow(args.get(0));
                    ServerUtils.send(sender, LangKeys.format("worldDisallowed", args.get(0)));
                    return true;
                }
                ServerUtils.send(sender, LangKeys.format("invalidWorldName"));
                return false;
            }
        });
        addSub(new IICommand("speed") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (args.isEmpty()) {
                    ServerUtils.send(sender, LangKeys.format("speedInfo", config.minSpeed(), config.maxSpeed()));
                } else if (args.get(0).matches("[0-9]+-[0-9]+")) {
                    String[] ss = args.get(0).split("-");
                    config.minSpeed(Integer.valueOf(ss[0]));
                    config.maxSpeed(Integer.valueOf(ss[1]));
                    ServerUtils.send(sender, LangKeys.format("speedInfo", config.minSpeed(), config.maxSpeed()));
                } else {
                    ServerUtils.send(sender, LangKeys.format("speedUsage"));
                }
                return true;
            }
        });
        addSub(new IICommand("health") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (args.isEmpty()) {
                    ServerUtils.send(sender, LangKeys.format("healthInfo", config.minHealth(), config.maxHealth()));
                } else if (args.get(0).matches("[0-9]+-[0-9]+")) {
                    String[] ss = args.get(0).split("-");
                    config.minHealth(Integer.valueOf(ss[0]));
                    config.maxHealth(Integer.valueOf(ss[1]));
                    ServerUtils.send(sender, LangKeys.format("healthInfo", config.minHealth(), config.maxHealth()));
                } else {
                    ServerUtils.send(sender, LangKeys.format("healthUsage"));
                }
                return true;
            }
        });
        addSub(new IICommand("radius") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (args.isEmpty()) {
                    ServerUtils.send(sender, LangKeys.format("radiusInfo", config.minSpawnRadius(), config.maxSpawnRadius()));
                } else if (args.get(0).matches("[0-9]+-[0-9]+")) {
                    String[] ss = args.get(0).split("-");
                    config.minSpawnRadius(Integer.valueOf(ss[0]));
                    config.maxSpawnRadius(Integer.valueOf(ss[1]));
                    ServerUtils.send(sender, LangKeys.format("radiusInfo", config.minSpawnRadius(), config.maxSpawnRadius()));
                } else {
                    ServerUtils.send(sender, LangKeys.format("radiusUsage"));
                }
                return true;
            }
        });
        addSub(new IICommand("refresh") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (args.isEmpty()) {
                    ServerUtils.send(sender, LangKeys.format("refreshTime", config.refresh()));
                } else if (args.get(0).matches("[0-9]+")) {
                    config.refresh(Integer.valueOf(args.get(0)));
                    SpawnTask.runNewTask(plugin, config);
                    ServerUtils.send(sender, LangKeys.format("refreshTime", config.refresh()));
                } else {
                    ServerUtils.send(sender, LangKeys.format("refreshUsage"));
                }
                return true;
            }
        });
        addSub(new IICommand("debug") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                config.debug(!config.debug());
                ServerUtils.send(sender, LangKeys.format(config.debug() ? "debugON" : "debugOFF"));
                return true;
            }
        });
        addSub(new IICommand("slot") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (args.isEmpty()) {
                    ServerUtils.send(sender, LangKeys.format("displaySlot" + config.displaySlot()));
                } else if (args.get(0).matches("[0-3]")) {
                    config.displaySlot(Integer.valueOf(args.get(0)));
                    ServerUtils.send(sender, LangKeys.format("displaySlot" + config.displaySlot()));
                } else {
                    ServerUtils.send(sender, LangKeys.format("slotUsage"));
                }
                return true;
            }
        });
        addSub(new IICommand("lang") {
            @Override
            public boolean execute(CommandSender sender, ArrayList<String> args) {
                if (args.isEmpty()) {
                    ServerUtils.send(sender, LangKeys.format("language", config.lang()));
                } else {
                    config.lang(args.get(0));
                    ServerUtils.send(sender, LangKeys.format("language", config.lang()));
                }
                return true;
            }
        });
    }

    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (args.size() >= 1) {
            IICommand sub = subs.get(args.remove(0));
            if (sub != null) {
                return sub.execute(sender, args);
            }
        }
        return false;
    }

}
