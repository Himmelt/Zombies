package org.soraworld.zombies.config;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.soraworld.violet.config.IIConfig;
import org.soraworld.zombies.constant.Constant;
import org.soraworld.zombies.flans.Flans;
import org.soraworld.zombies.scoreboard.ScoreBoards;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Config extends IIConfig {

    private int minSpeed = 0;
    private int maxSpeed = 1;
    private int minHealth = 10;
    private int maxHealth = 20;
    private int minSpawnRadius = 15;
    private int maxSpawnRadius = 25;
    private int refresh = 50;
    private int spawnLimit = 30;
    private int killCoolLimit = 80;
    private int killCoolTime = 300;
    private double babyChance = 0.0F;
    private boolean customDrops = false;
    private int minDropExp = 0;
    private int maxDropExp = 0;
    private int displaySlot = 0;
    private final HashSet<String> allowWorlds = new HashSet<>();

    private final ScoreBoards killsBoard = new ScoreBoards();
    private final HashMap<String, Integer> kills = new HashMap<>();

    public Config(File path, Plugin plugin) {
        super(path, plugin);
    }

    public boolean customDrops() {
        return customDrops;
    }

    protected void loadOptions() {
        allowWorlds.clear();
        // speed
        String speed = config_yaml.getString("speed");
        if (speed != null && speed.matches("[0-9]+-[0-9]+")) {
            String[] ss = speed.split("-");
            minSpeed = Integer.valueOf(ss[0]);
            maxSpeed = Integer.valueOf(ss[1]);
        }
        // health
        String health = config_yaml.getString("health");
        if (health != null && health.matches("[0-9]+-[0-9]+")) {
            String[] ss = health.split("-");
            minHealth = Integer.valueOf(ss[0]);
            maxHealth = Integer.valueOf(ss[1]);
        }
        // spawnRadius
        String spawnRadius = config_yaml.getString("spawnRadius");
        if (spawnRadius != null && spawnRadius.matches("[0-9]+-[0-9]+")) {
            String[] ss = spawnRadius.split("-");
            minSpawnRadius = Integer.valueOf(ss[0]);
            maxSpawnRadius = Integer.valueOf(ss[1]);
        }
        refresh = config_yaml.getInt("refresh");
        spawnLimit = config_yaml.getInt("spawnLimit");
        killCoolLimit = config_yaml.getInt("killCoolLimit");
        killCoolTime = config_yaml.getInt("killCoolTime");
        babyChance = config_yaml.getDouble("babyChance");
        customDrops = config_yaml.getBoolean("customDrops");
        // dropExp
        String dropExp = config_yaml.getString("dropExp");
        if (dropExp != null && dropExp.matches("[0-9]+-[0-9]+")) {
            String[] ss = dropExp.split("-");
            minDropExp = Integer.valueOf(ss[0]);
            maxDropExp = Integer.valueOf(ss[1]);
        }
        displaySlot = config_yaml.getInt("displaySlot");
        allowWorlds.addAll(config_yaml.getStringList("allowWorlds"));

        if (displaySlot < 0 || displaySlot > 3) displaySlot = 0;
        killsBoard.setDisplaySlot(displaySlot);
        killsBoard.setDisplayName(iiLang.format("scoreboardDisplay"));
    }

    protected void saveOptions() {
        config_yaml.set("speed", minSpeed + "-" + maxSpeed);
        config_yaml.set("health", minHealth + "-" + maxHealth);
        config_yaml.set("spawnRadius", minSpawnRadius + "-" + maxSpawnRadius);
        config_yaml.set("refresh", refresh);
        config_yaml.set("spawnLimit", spawnLimit);
        config_yaml.set("killCoolLimit", killCoolLimit);
        config_yaml.set("killCoolTime", killCoolTime);
        config_yaml.set("babyChance", babyChance);
        config_yaml.set("customDrops", customDrops);
        config_yaml.set("dropExp", minDropExp + "-" + maxDropExp);
        config_yaml.set("displaySlot", displaySlot);
        config_yaml.set("allowWorlds", new ArrayList<>(allowWorlds));
    }

    public void afterLoad() {
        Flans.checkFlans(this);
    }

    @Nonnull
    protected ChatColor defaultChatColor() {
        return ChatColor.RED;
    }

    @Nonnull
    protected String defaultChatHead() {
        return "[" + Constant.PLUGIN_NAME + "] ";
    }

    public String defaultAdminPerm() {
        return Constant.PERM_ADMIN;
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

    public int spawnLimit() {
        return spawnLimit;
    }

    public int maxSpawnRadius() {
        return maxSpawnRadius;
    }

    public int killCoolTime() {
        return killCoolTime;
    }

    public void clearKills(String name) {
        kills.remove(name);
    }

    public void addKill(String name) {
        if (name == null || name.isEmpty()) return;
        int kill = getKill(name);
        kills.put(name, kill + 1);
        killsBoard.update(name, kill + 1);
        if (debug) console("debugKillCount", name, kill + 1);
    }

    public void displaySlot(int slot) {
        displaySlot = slot;
        if (displaySlot < 0 || displaySlot > 3) displaySlot = 0;
        killsBoard.setDisplaySlot(slot);
    }

    public boolean killCool(String name) {
        int kill = getKill(name);
        if (debug && kill >= killCoolLimit) console("debugKillCool", name, kill, killCoolLimit);
        return kill >= killCoolLimit;
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
        return (int) (minDropExp + Math.abs(maxDropExp - minDropExp) * Math.random());
    }

    public int displaySlot() {
        return displaySlot;
    }

}
