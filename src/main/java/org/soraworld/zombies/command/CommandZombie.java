package org.soraworld.zombies.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.soraworld.violet.command.CommandViolet;
import org.soraworld.violet.command.IICommand;
import org.soraworld.violet.constant.Violets;
import org.soraworld.zombies.config.Config;

import java.util.List;

public class CommandZombie extends CommandViolet {

    public CommandZombie(String name, String perm, final Config config) {
        super(name, perm, config);
        addSub(new IICommand("slot", config) {
            public boolean execute(CommandSender sender, List<String> args) {
                if (args.isEmpty()) {
                    config.send(sender, "displaySlot" + config.displaySlot());
                } else if (args.get(0).matches("[0-3]")) {
                    config.displaySlot(Integer.valueOf(args.get(0)));
                    config.send(sender, "displaySlot" + config.displaySlot());
                } else {
                    config.send(sender, "slotUsage");
                }
                return true;
            }
        });
        addSub(new IICommand("allow", config) {
            public boolean execute(CommandSender sender, List<String> args) {
                if (sender instanceof Player) {
                    if (args.isEmpty()) {
                        String world = ((Player) sender).getWorld().getName();
                        config.allow(world);
                        config.send(sender, "worldAllowed", world);
                    } else {
                        config.allow(args.get(0));
                        config.send(sender, "worldAllowed", args.get(0));
                    }
                    return true;
                }
                if (!args.isEmpty()) {
                    config.allow(args.get(0));
                    config.send(sender, "worldAllowed", args.get(0));
                    return true;
                }
                config.sendV(sender, Violets.KEY_INVALID_ARG);
                return false;
            }
        });
        addSub(new IICommand("disallow", config) {
            public boolean execute(CommandSender sender, List<String> args) {
                if (sender instanceof Player) {
                    if (args.isEmpty()) {
                        String worldName = ((Player) sender).getWorld().getName();
                        config.disallow(worldName);
                        config.send(sender, "worldDisallowed", worldName);
                    } else {
                        config.disallow(args.get(0));
                        config.send(sender, "worldDisallowed", args.get(0));
                    }
                    return true;
                }
                if (!args.isEmpty()) {
                    config.disallow(args.get(0));
                    config.send(sender, "worldDisallowed", args.get(0));
                    return true;
                }
                config.send(sender, "invalidWorldName");
                return false;
            }
        });
    }

}
