package org.soraworld.zombies.config;

import org.bukkit.ChatColor;
import org.soraworld.violet.config.VioletManager;
import org.soraworld.zombies.flans.Flans;
import org.soraworld.zombies.scoreboard.ScoreBoards;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class ZombiesManager extends VioletManager {

    private final ZombiesSetting ss;
    private final ScoreBoards killsBoard = new ScoreBoards();
    private final HashMap<String, Integer> kills = new HashMap<>();

    public ZombiesManager(Path path) {
        super(path, new ZombiesSetting());
        ss = (ZombiesSetting) setting;
    }

    public boolean customDrops() {
        return ss.customDrops;
    }

    protected void loadOptions() {
        ss.allowWorlds.clear();
        // speed
        String speed = getCfgYaml().getString("speed");
        if (speed != null && speed.matches("[0-9]+-[0-9]+")) {
            String[] ss = speed.split("-");
            this.ss.minSpeed = Integer.valueOf(ss[0]);
            this.ss.maxSpeed = Integer.valueOf(ss[1]);
        }
        // health
        String health = getCfgYaml().getString("health");
        if (health != null && health.matches("[0-9]+-[0-9]+")) {
            String[] ss = health.split("-");
            this.ss.minHealth = Integer.valueOf(ss[0]);
            this.ss.maxHealth = Integer.valueOf(ss[1]);
        }
        // spawnRadius
        String spawnRadius = getCfgYaml().getString("spawnRadius");
        if (spawnRadius != null && spawnRadius.matches("[0-9]+-[0-9]+")) {
            String[] ss = spawnRadius.split("-");
            this.ss.minSpawnRadius = Integer.valueOf(ss[0]);
            this.ss.maxSpawnRadius = Integer.valueOf(ss[1]);
        }
        ss.refresh = getCfgYaml().getInt("refresh");
        ss.spawnLimit = getCfgYaml().getInt("spawnLimit");
        ss.killCoolLimit = getCfgYaml().getInt("killCoolLimit");
        ss.killCoolTime = getCfgYaml().getInt("killCoolTime");
        ss.babyChance = getCfgYaml().getDouble("babyChance");
        ss.customDrops = getCfgYaml().getBoolean("customDrops");
        // dropExp
        String dropExp = getCfgYaml().getString("dropExp");
        if (dropExp != null && dropExp.matches("[0-9]+-[0-9]+")) {
            String[] ss = dropExp.split("-");
            this.ss.minDropExp = Integer.valueOf(ss[0]);
            this.ss.maxDropExp = Integer.valueOf(ss[1]);
        }
        ss.displaySlot = getCfgYaml().getInt("displaySlot");
        ss.allowWorlds.addAll(getCfgYaml().getStringList("allowWorlds"));

        if (ss.displaySlot < 0 || ss.displaySlot > 3) ss.displaySlot = 0;
        killsBoard.setDisplaySlot(ss.displaySlot);
        killsBoard.setDisplayName(formatKey("scoreboardDisplay").replace('&', ChatColor.COLOR_CHAR));
    }

    protected void saveOptions() {
        getCfgYaml().set("speed", ss.minSpeed + "-" + ss.maxSpeed);
        getCfgYaml().set("health", ss.minHealth + "-" + ss.maxHealth);
        getCfgYaml().set("spawnRadius", ss.minSpawnRadius + "-" + ss.maxSpawnRadius);
        getCfgYaml().set("refresh", ss.refresh);
        getCfgYaml().set("spawnLimit", ss.spawnLimit);
        getCfgYaml().set("killCoolLimit", ss.killCoolLimit);
        getCfgYaml().set("killCoolTime", ss.killCoolTime);
        getCfgYaml().set("babyChance", ss.babyChance);
        getCfgYaml().set("customDrops", ss.customDrops);
        getCfgYaml().set("dropExp", ss.minDropExp + "-" + ss.maxDropExp);
        getCfgYaml().set("displaySlot", ss.displaySlot);
        getCfgYaml().set("allowWorlds", new ArrayList<>(ss.allowWorlds));
    }

    public void afterLoad() {
        Flans.checkFlans(this);
    }

    public int randSpeed() {
        return (int) (ss.minSpeed + Math.abs(ss.maxSpeed - ss.minSpeed) * Math.random());
    }

    public double randHealth() {
        return ss.minHealth + Math.abs(ss.maxHealth - ss.minHealth) * Math.random();
    }

    public void allow(String world) {
        ss.allowWorlds.add(world);
        save();
    }

    public void disallow(String world) {
        ss.allowWorlds.remove(world);
        save();
    }

    public boolean isAllow(String world) {
        return ss.allowWorlds.contains(world);
    }

    public double getBabyChance() {
        return ss.babyChance > 0 ? ss.babyChance > 1 ? 1 : ss.babyChance : 0;
    }

    public long refresh() {
        if (ss.refresh < 1) ss.refresh = 1;
        return ss.refresh;
    }

    public double randSpawnRadius() {
        return ss.minSpawnRadius + Math.abs(ss.maxSpawnRadius - ss.minSpawnRadius) * Math.random();
    }

    public int spawnLimit() {
        return ss.spawnLimit;
    }

    public int maxSpawnRadius() {
        return ss.maxSpawnRadius;
    }

    public int killCoolTime() {
        return ss.killCoolTime;
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
        ss.displaySlot = slot;
        if (ss.displaySlot < 0 || ss.displaySlot > 3) ss.displaySlot = 0;
        killsBoard.setDisplaySlot(slot);
    }

    public boolean killCool(String name) {
        int kill = getKill(name);
        if (debug && kill >= ss.killCoolLimit) consoleKey("debugKillCool", name, kill, ss.killCoolLimit);
        return kill >= ss.killCoolLimit;
    }

    private int getKill(String name) {
        Integer kill = kills.get(name);
        if (kill == null) {
            kill = 0;
            kills.put(name, kill);
        }
        return kill;
    }

    public int randDropExp() {
        return (int) (ss.minDropExp + Math.abs(ss.maxDropExp - ss.minDropExp) * Math.random());
    }

    public int displaySlot() {
        return ss.displaySlot;
    }

}
