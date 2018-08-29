package org.soraworld.zombies.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.soraworld.violet.command.Paths;
import org.soraworld.violet.command.Sub;
import org.soraworld.violet.manager.SpigotManager;
import org.soraworld.zombies.manager.ZombiesManager;

public class CommandZombie {
    @Sub(perm = "admin", tabs = {"0", "1", "2", "3"})
    public static void slot(SpigotManager manager, CommandSender sender, Paths args) {
        if (manager instanceof ZombiesManager) {
            ZombiesManager zbm = (ZombiesManager) manager;
            if (args.empty()) {
                zbm.sendKey(sender, "displaySlot" + zbm.displaySlot);
            } else if (args.get(0).matches("[0-3]")) {
                zbm.displaySlot(Integer.valueOf(args.get(0)));
                zbm.sendKey(sender, "displaySlot" + zbm.displaySlot);
            } else zbm.sendKey(sender, "slotUsage");
        }
    }

    @Sub(perm = "admin", tabs = {"world"})
    public static void allow(SpigotManager manager, CommandSender sender, Paths args) {
        if (manager instanceof ZombiesManager) {
            ZombiesManager zbm = (ZombiesManager) manager;
            if (sender instanceof Player) {
                if (args.empty()) {
                    String world = ((Player) sender).getWorld().getName();
                    zbm.allow(world);
                    zbm.sendKey(sender, "worldAllowed", world);
                } else {
                    zbm.allow(args.get(0));
                    zbm.sendKey(sender, "worldAllowed", args.get(0));
                }
            } else if (args.notEmpty()) {
                zbm.allow(args.first());
                zbm.sendKey(sender, "worldAllowed", args.first());
            } else zbm.sendKey(sender, "invalidArg");
        }
    }

    @Sub(perm = "admin", tabs = {"world"})
    public static void disallow(SpigotManager manager, CommandSender sender, Paths args) {
        if (manager instanceof ZombiesManager) {
            ZombiesManager zbm = (ZombiesManager) manager;
            if (sender instanceof Player) {
                if (args.empty()) {
                    String worldName = ((Player) sender).getWorld().getName();
                    zbm.disallow(worldName);
                    zbm.sendKey(sender, "worldDisallowed", worldName);
                } else {
                    zbm.disallow(args.get(0));
                    zbm.sendKey(sender, "worldDisallowed", args.get(0));
                }
            } else if (args.notEmpty()) {
                zbm.disallow(args.get(0));
                zbm.sendKey(sender, "worldDisallowed", args.get(0));
            } else zbm.sendKey(sender, "invalidWorldName");
        }
    }
}
