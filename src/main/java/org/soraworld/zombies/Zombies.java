package org.soraworld.zombies;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.soraworld.zombies.command.CommandZombie;
import org.soraworld.zombies.config.Config;
import org.soraworld.zombies.listener.EventListener;
import org.soraworld.zombies.task.SpawnTask;
import org.soraworld.zombies.util.Lists;

public class Zombies extends JavaPlugin {

    private static Zombies instance;

    private Config config;
    private CommandZombie command;

    public Zombies() {
        instance = this;
    }

    public static Zombies getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        config = new Config(this.getDataFolder());
        config.load();
        config.save();
        command = new CommandZombie("zombie", config);
        this.getServer().getPluginManager().registerEvents(new EventListener(config), this);
        SpawnTask task = new SpawnTask(config);
        task.runTaskTimer(this, config.refresh(), config.refresh());
    }

    @Override
    public void onDisable() {
        config.save();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return command.execute(sender, Lists.arrayList(args));
    }
}
