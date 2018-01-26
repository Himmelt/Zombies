package org.soraworld.zombies.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.soraworld.zombies.util.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public abstract class IICommand implements CommandExecutor {

    private final String name;
    private final List<String> aliases;
    protected final TreeMap<String, IICommand> subs = new TreeMap<>();

    public IICommand(String name, String... aliases) {
        this.name = name;
        this.aliases = ListUtils.arrayList(aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
        return execute(sender, ListUtils.arrayList(args));
    }

    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (args.size() >= 1) {
            IICommand sub = subs.get(args.remove(0));
            if (sub != null) {
                return sub.execute(sender, args);
            }
        }
        sender.sendMessage(getUsage());
        return false;
    }

    public String getUsage() {
        return "";
    }

    final void addSub(IICommand sub) {
        this.subs.put(sub.name, sub);
        for (String alias : sub.aliases) {
            IICommand command = this.subs.get(alias);
            if (command == null || !command.name.equals(alias)) {
                this.subs.put(alias, sub);
            }
        }
    }

}
