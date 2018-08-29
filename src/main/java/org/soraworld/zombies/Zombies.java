package org.soraworld.zombies;

import org.bukkit.event.Listener;
import org.soraworld.violet.command.SpigotBaseSubs;
import org.soraworld.violet.command.SpigotCommand;
import org.soraworld.violet.manager.SpigotManager;
import org.soraworld.violet.plugin.SpigotPlugin;
import org.soraworld.zombies.command.CommandZombie;
import org.soraworld.zombies.listener.EventListener;
import org.soraworld.zombies.manager.ZombiesManager;
import org.soraworld.zombies.task.SpawnTask;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class Zombies extends SpigotPlugin {

    public String assetsId() {
        return getId();
    }

    public void afterEnable() {
        SpawnTask.runNewTask(this, (ZombiesManager) manager);
    }

    protected SpigotManager registerManager(Path path) {
        return new ZombiesManager(this, path);
    }

    protected List<Listener> registerListeners() {
        return Collections.singletonList(new EventListener((ZombiesManager) manager));
    }

    protected void registerCommands() {
        SpigotCommand command = new SpigotCommand(this.getId(), this.manager.defAdminPerm(), false, this.manager);
        command.extractSub(SpigotBaseSubs.class, "lang");
        command.extractSub(SpigotBaseSubs.class, "debug");
        command.extractSub(SpigotBaseSubs.class, "save");
        command.extractSub(SpigotBaseSubs.class, "reload");
        command.extractSub(CommandZombie.class);
        command.setUsage("/zombies allow|disallow|slot");
        register(this, command);
    }
}
