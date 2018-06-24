package org.soraworld.zombies;

import org.soraworld.violet.VioletBukkit;
import org.soraworld.zombies.command.CommandZombie;
import org.soraworld.zombies.config.ZombiesManager;
import org.soraworld.zombies.constant.Constant;

import java.nio.file.Path;

public class ZombiesBukkit extends VioletBukkit {
    protected void initPlugin(Path path) {
        this.manager = new ZombiesManager(this, path);
        this.manager.load();
        this.command = new CommandZombie(Constant.PERM_ADMIN, (ZombiesManager) this.manager);
    }

    public String getId() {
        return Constant.PLUGIN_ID;
    }

}
