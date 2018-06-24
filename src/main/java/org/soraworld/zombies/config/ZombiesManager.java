package org.soraworld.zombies.config;

import ninja.leaping.configurate.objectmapping.Setting;
import org.soraworld.violet.config.VioletManager;
import org.soraworld.zombies.flans.Flans;
import org.soraworld.zombies.scoreboard.ScoreBoards;
import rikka.api.IPlugin;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;

public class ZombiesManager extends VioletManager {

    @Setting
    public int minSpeed = 0;
    @Setting
    public int maxSpeed = 1;
    @Setting
    public int minHealth = 10;
    @Setting
    public int maxHealth = 20;
    @Setting
    public int minSpawnRadius = 15;
    @Setting
    public int maxSpawnRadius = 25;
    @Setting
    public int refresh = 50;
    @Setting
    public int spawnLimit = 30;
    @Setting
    public int killCoolLimit = 80;
    @Setting
    public int killCoolTime = 300;
    @Setting
    public double babyChance = 0.0F;
    @Setting
    public boolean customDrops = false;
    @Setting
    public int minDropExp = 0;
    @Setting
    public int maxDropExp = 0;
    @Setting
    public int displaySlot = 0;
    @Setting
    public final HashSet<String> allowWorlds = new HashSet<>();


    private final ScoreBoards killsBoard = new ScoreBoards();
    private final HashMap<String, Integer> kills = new HashMap<>();

    public ZombiesManager(IPlugin plugin, Path path) {
        super(plugin, path);
    }

    public void afterLoad() {
        Flans.checkFlans(this);
    }

    public int randSpeed() {
        return (int) (minSpeed + Math.abs(maxSpeed - minSpeed) * Math.random());
    }

    public double randHealth() {
        return minHealth + Math.abs(maxHealth - minHealth) * Math.random();
    }

    public void allow(String world) {
        allowWorlds.add(world);
        save();
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
        //killsBoard.update(name, kill + 1);
        if (debug) consoleKey("debugKillCount", name, kill + 1);
    }

    public void displaySlot(int slot) {
        displaySlot = slot;
        if (displaySlot < 0 || displaySlot > 3) displaySlot = 0;
        //killsBoard.setDisplaySlot(slot);
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
