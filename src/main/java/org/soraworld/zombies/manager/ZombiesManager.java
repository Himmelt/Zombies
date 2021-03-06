package org.soraworld.zombies.manager;

import org.soraworld.hocon.node.Setting;
import org.soraworld.violet.manager.SpigotManager;
import org.soraworld.violet.plugin.SpigotPlugin;
import org.soraworld.violet.util.ChatColor;
import org.soraworld.zombies.flans.Flans;
import org.soraworld.zombies.scoreboard.ScoreBoards;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class ZombiesManager extends SpigotManager {

    @Setting(comment = "comment.minSpeed")
    public int minSpeed = 0;
    @Setting(comment = "comment.maxSpeed")
    public int maxSpeed = 1;
    @Setting(comment = "comment.minHealth")
    public int minHealth = 10;
    @Setting(comment = "comment.maxHealth")
    public int maxHealth = 20;
    @Setting(comment = "comment.minSpawnRadius")
    public int minSpawnRadius = 15;
    @Setting(comment = "comment.maxSpawnRadius")
    public int maxSpawnRadius = 25;
    @Setting(comment = "comment.refresh")
    public int refresh = 50;
    @Setting(comment = "comment.spawnLimit")
    public int spawnLimit = 30;
    @Setting(comment = "comment.killCoolLimit")
    public int killCoolLimit = 80;
    @Setting(comment = "comment.killCoolTime")
    public int killCoolTime = 300;
    @Setting(comment = "comment.babyChance")
    public double babyChance = 0.0F;
    @Setting(comment = "comment.customDrops")
    public boolean customDrops = false;
    @Setting(comment = "comment.minDropExp")
    public int minDropExp = 0;
    @Setting(comment = "comment.maxDropExp")
    public int maxDropExp = 0;
    @Setting(comment = "comment.displaySlot")
    public int displaySlot = 0;
    @Setting(comment = "comment.allowWorlds")
    public ArrayList<String> allowWorlds = new ArrayList<>();

    private final ScoreBoards killsBoard = new ScoreBoards();
    private final HashMap<String, Integer> kills = new HashMap<>();

    public ZombiesManager(SpigotPlugin plugin, Path path) {
        super(plugin, path);
    }

    public ChatColor defChatColor() {
        return ChatColor.AQUA;
    }

    public void afterLoad() {
        if (displaySlot < 0 || displaySlot > 3) displaySlot = 0;
        killsBoard.setDisplaySlot(displaySlot);
        killsBoard.setDisplayName(trans("scoreboardDisplay"));
        Flans.checkFlans(this);
    }

    public int randSpeed() {
        return (int) (minSpeed + Math.abs(maxSpeed - minSpeed) * Math.random());
    }

    public double randHealth() {
        return minHealth + Math.abs(maxHealth - minHealth) * Math.random();
    }

    public void allow(String world) {
        if (!allowWorlds.contains(world)) {
            allowWorlds.add(world);
            save();
        }
    }

    public void disallow(String world) {
        allowWorlds.remove(world);
        save();
    }

    public boolean isAllow(String world) {
        return allowWorlds.contains(world);
    }

    public double getBabyChance() {
        return babyChance > 0 ? babyChance > 1 ? 1 : babyChance : 0;
    }

    public long refresh() {
        if (refresh < 1) refresh = 1;
        return refresh;
    }

    public double randSpawnRadius() {
        return minSpawnRadius + Math.abs(maxSpawnRadius - minSpawnRadius) * Math.random();
    }

    public void clearKills(String name) {
        kills.remove(name);
    }

    public void addKill(String name) {
        if (name == null || name.isEmpty()) return;
        int kill = getKill(name);
        kills.put(name, kill + 1);
        killsBoard.update(name, kill + 1);
        if (debug) consoleKey("debugKillCount", name, kill + 1);
    }

    public void displaySlot(int slot) {
        displaySlot = slot;
        if (displaySlot < 0 || displaySlot > 3) displaySlot = 0;
        killsBoard.setDisplaySlot(slot);
    }

    public boolean killCool(String name) {
        int kill = getKill(name);
        if (debug && kill >= killCoolLimit) consoleKey("debugKillCool", name, kill, killCoolLimit);
        return kill >= killCoolLimit;
    }

    private int getKill(String name) {
        return kills.computeIfAbsent(name, k -> 0);
    }

    public int randDropExp() {
        return (int) (minDropExp + Math.abs(maxDropExp - minDropExp) * Math.random());
    }
}
