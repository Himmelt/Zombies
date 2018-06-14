package org.soraworld.zombies.command;

import org.bukkit.entity.Player;
import org.soraworld.violet.command.VioletCommand;
import org.soraworld.violet.constant.Violets;
import org.soraworld.zombies.config.ZombiesManager;
import rikka.api.command.CommandArgs;
import rikka.api.command.ExecuteResult;
import rikka.api.command.ICommandSender;
import rikka.api.command.IICommand;

public class CommandZombie extends VioletCommand {

    public CommandZombie(String name, String perm, final ZombiesManager manager) {
        super(perm, false, manager, "zombie");
        addSub(new IICommand(perm, false, "slot") {
            public ExecuteResult execute(ICommandSender sender, CommandArgs args) {
                if (args.empty()) {
                    manager.sendKey(sender, "displaySlot" + manager.displaySlot());
                } else if (args.get(0).matches("[0-3]")) {
                    manager.displaySlot(Integer.valueOf(args.get(0)));
                    manager.sendKey(sender, "displaySlot" + manager.displaySlot());
                } else {
                    manager.sendKey(sender, "slotUsage");
                }
                return ExecuteResult.SUCCESS;
            }
        });
        addSub(new IICommand(perm, false, "allow") {
            public ExecuteResult execute(ICommandSender sender, CommandArgs args) {
                if (sender instanceof Player) {
                    if (args.empty()) {
                        String world = ((Player) sender).getWorld().getName();
                        manager.allow(world);
                        manager.sendKey(sender, "worldAllowed", world);
                    } else {
                        manager.allow(args.get(0));
                        manager.sendKey(sender, "worldAllowed", args.get(0));
                    }
                    return ExecuteResult.SUCCESS;
                }
                if (args.notEmpty()) {
                    manager.allow(args.get(0));
                    manager.sendKey(sender, "worldAllowed", args.get(0));
                    return ExecuteResult.SUCCESS;
                }
                manager.sendVKey(sender, Violets.KEY_INVALID_ARG);
                return ExecuteResult.FAILED;
            }
        });
        addSub(new IICommand(perm, false, "disallow") {
            public ExecuteResult execute(ICommandSender sender, CommandArgs args) {
                if (sender instanceof Player) {
                    if (args.empty()) {
                        String worldName = ((Player) sender).getWorld().getName();
                        manager.disallow(worldName);
                        manager.sendKey(sender, "worldDisallowed", worldName);
                    } else {
                        manager.disallow(args.get(0));
                        manager.sendKey(sender, "worldDisallowed", args.get(0));
                    }
                    return ExecuteResult.SUCCESS;
                }
                if (args.notEmpty()) {
                    manager.disallow(args.get(0));
                    manager.sendKey(sender, "worldDisallowed", args.get(0));
                    return ExecuteResult.SUCCESS;
                }
                manager.sendKey(sender, "invalidWorldName");
                return ExecuteResult.FAILED;
            }
        });
    }

}
