package org.soraworld.zombies;

import org.bukkit.event.Listener;
import org.soraworld.violet.VioletPlugin;
import org.soraworld.violet.config.IIConfig;
import org.soraworld.zombies.config.Config;
import org.soraworld.zombies.task.SpawnTask;

import java.io.File;
import java.util.List;

public class Zombies extends VioletPlugin {

    protected IIConfig registerConfig(File file) {
        return null;
    }

    protected List<Listener> registerEvents(IIConfig iiConfig) {
        return null;
    }

    protected org.soraworld.violet.command.IICommand registerCommand(IIConfig iiConfig) {
        return null;
    }

    protected void afterEnable() {
        if (config instanceof Config) SpawnTask.runNewTask(this, config);
    }

    protected void beforeDisable() {

    }

}
