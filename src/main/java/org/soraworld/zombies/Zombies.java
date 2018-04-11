package org.soraworld.zombies;

import org.bukkit.event.Listener;
import org.soraworld.violet.VioletPlugin;
import org.soraworld.violet.command.IICommand;
import org.soraworld.violet.config.IIConfig;
import org.soraworld.zombies.command.CommandZombie;
import org.soraworld.zombies.config.Config;
import org.soraworld.zombies.constant.Constant;
import org.soraworld.zombies.listener.EventListener;
import org.soraworld.zombies.task.SpawnTask;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Zombies extends VioletPlugin {

    @Nonnull
    protected IIConfig registerConfig(File path) {
        return new Config(path, this);
    }

    @Nonnull
    protected List<Listener> registerEvents(IIConfig config) {
        ArrayList<Listener> listeners = new ArrayList<>();
        if (config instanceof Config) listeners.add(new EventListener((Config) config));
        return listeners;
    }

    protected IICommand registerCommand(IIConfig config) {
        if (config instanceof Config) {
            return new CommandZombie(Constant.PLUGIN_ID, Constant.PERM_ADMIN, (Config) config, this);
        }
        return null;
    }

    protected void afterEnable() {
        if (config instanceof Config) SpawnTask.runNewTask(this, (Config) config);
    }

    protected void beforeDisable() {

    }

}
