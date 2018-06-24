package org.soraworld.zombies;

import org.soraworld.violet.VioletSponge;
import org.soraworld.zombies.command.CommandZombie;
import org.soraworld.zombies.config.ZombiesManager;
import org.soraworld.zombies.constant.Constant;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;

@Plugin(
        id = Constant.PLUGIN_ID,
        name = Constant.PLUGIN_NAME,
        version = Constant.PLUGIN_VERSION,
        authors = {"Himmelt"},
        dependencies = {@Dependency(id = "violet")},
        url = "https://github.com/Himmelt/Zombies",
        description = "Zombies Plugin."
)
public class ZombiesSponge extends VioletSponge {
    protected void initPlugin(Path path) {
        this.manager = new ZombiesManager(this, path);
        this.manager.load();
        this.command = new CommandZombie(Constant.PERM_ADMIN, (ZombiesManager) this.manager);
    }

    public String getId() {
        return Constant.PLUGIN_ID;
    }
}
