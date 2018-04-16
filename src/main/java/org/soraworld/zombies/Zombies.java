package org.soraworld.zombies;

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

public class Zombies extends VioletPlugin {

    @Nonnull
    protected IIConfig registerConfig(File path) {
        return new Config(path);
    }

    protected void registerEvents() {
        if (iconfig instanceof Config) registerEvent(new EventListener((Config) iconfig));
    }

    protected IICommand registerCommand() {
        if (iconfig instanceof Config) {
            return new CommandZombie(Constant.PLUGIN_ID, Constant.PERM_ADMIN, (Config) iconfig);
        }
        return null;
    }

    protected void afterEnable() {
        if (iconfig instanceof Config) SpawnTask.runNewTask(this, (Config) iconfig);
    }

    protected void beforeDisable() {

    }

}
