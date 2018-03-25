package org.soraworld.zombies;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.soraworld.zombies.command.CommandZombie;
import org.soraworld.zombies.command.IICommand;
import org.soraworld.zombies.config.Config;
import org.soraworld.zombies.listener.EventListener;
import org.soraworld.zombies.task.SpawnTask;
import org.soraworld.zombies.util.ListUtils;

import java.util.List;

public class Zombies extends JavaPlugin {

    private Config config;
    private IICommand command;

    @Override
    public void onEnable() {
        config = new Config(this.getDataFolder());
        config.load();
        config.save();
        this.getServer().getPluginManager().registerEvents(new EventListener(config), this);
        SpawnTask.runNewTask(this, config);
        command = new CommandZombie("zombie", this, config);
    }

    @Override
    public void onDisable() {
        config.save();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return command.execute(sender, ListUtils.arrayList(args));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        return command.onTabComplete(sender, cmd, alias, args);
    }

}
