package org.soraworld.zombies.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Config {

    private int minSpeed = 0;
    private int maxSpeed = 2;
    private int minHealth = 10;
    private int maxHealth = 20;
    private int minSpawnRadius = 20;
    private int maxSpawnRadius = 20;
    private int refresh = 5;
    private int spawnLimit = 80;
    private int killCoolLimit = 80;
    private int killCoolTime = 300;
    private double babyChance = 0.0F;
    private boolean noDrops = false;
    private final HashSet<String> allowWorlds = new HashSet<>();


    private final File file;
    private final HashMap<String, Integer> kills = new HashMap<>();
    private final YamlConfiguration config = new YamlConfiguration();

    public Config(File file) {
        this.file = new File(file, "config.yml");
    }

    public void load() {
        if (!file.exists()) {
            save();
            return;
        }
        try {
            config.load(file);
            // speed
            String speed = config.getString("speed");
            if (speed != null && speed.matches("[0-9]+-[0-9]+")) {
                String[] ss = speed.split("-");
                minSpeed = Integer.valueOf(ss[0]);
                maxSpeed = Integer.valueOf(ss[1]);
            }
            // health
            String health = config.getString("health");
            if (health != null && health.matches("[0-9]+-[0-9]+")) {
                String[] ss = health.split("-");
                minHealth = Integer.valueOf(ss[0]);
                maxHealth = Integer.valueOf(ss[1]);
            }
            // spawnRadius
            String spawnRadius = config.getString("spawnRadius");
            if (spawnRadius != null && spawnRadius.matches("[0-9]+-[0-9]+")) {
                String[] ss = spawnRadius.split("-");
                minSpawnRadius = Integer.valueOf(ss[0]);
                maxSpawnRadius = Integer.valueOf(ss[1]);
            }
            //
            refresh = config.getInt("refresh");
            spawnLimit = config.getInt("spawnLimit");
            killCoolLimit = config.getInt("killCoolLimit");
            killCoolTime = config.getInt("killCoolTime");
            babyChance = config.getDouble("babyChance");
            noDrops = config.getBoolean("noDrops");
            allowWorlds.clear();
            allowWorlds.addAll(config.getStringList("allowWorlds"));

        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            config.set("speed", minSpeed + "-" + maxSpeed);
            config.set("health", minHealth + "-" + maxHealth);
            config.set("spawnRadius", minSpawnRadius + "-" + maxSpawnRadius);
            config.set("refresh", refresh);
            config.set("spawnLimit", spawnLimit);
            config.set("killCoolLimit", killCoolLimit);
            config.set("killCoolTime", killCoolTime);
            config.set("babyChance", babyChance);
            config.set("noDrops", noDrops);
            config.set("allowWorlds", new ArrayList<>(allowWorlds));
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean noDrops() {
        return noDrops;
    }

    public ArrayList<ItemStack> drops() {
        return null;
    }

    public int randSpeed() {
        return 0;
    }

    public int speedDuration() {
        return 0;
    }

    public double randHealth() {
        return minHealth + Math.abs(maxHealth - minHealth) * Math.random();
    }

    public void allow(String world) {
        allowWorlds.add(world);
    }

    public void disallow(String world) {
        allowWorlds.remove(world);
    }

    public boolean isAllow(String world) {
        return allowWorlds.contains(world);
    }

    public double getBabyChance() {
        return babyChance > 0 ? babyChance > 1 ? 1 : babyChance : 0;
    }

    public long refresh() {
        if (refresh < 1) refresh = 1;
        return refresh * 20;
    }

    public void refresh(int refresh) {
        if (refresh < 1) refresh = 1;
        this.refresh = refresh;
    }

    public int maxSpeed() {
        return maxSpeed;
    }

    public int minSpeed() {
        return minSpeed;
    }

    public void maxSpeed(int speed) {
        this.maxSpeed = speed;
    }

    public void minSpeed(int speed) {
        this.minSpeed = speed;
    }

    public int maxHealth() {
        return maxHealth;
    }

    public int minHealth() {
        return minHealth;
    }

    public void maxHealth(int health) {
        this.maxHealth = health;
    }

    public void minHealth(int health) {
        this.minHealth = health;
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

    public int minSpawnRadius() {
        return minSpawnRadius;
    }

    public void maxSpawnRadius(int radius) {
        this.maxSpawnRadius = radius;
    }

    public void minSpawnRadius(int radius) {
        this.minSpawnRadius = radius;
    }

    public int killCoolTime() {
        return killCoolTime;
    }

    public void clearKills(String name) {
        kills.remove(name);
    }

    public void addKill(String name) {
        Integer kill = kills.get(name);
        if (kill == null) kill = 0;
        kills.put(name, kill + 1);
    }

    public boolean killCool(String name) {
        Integer kill = kills.get(name);
        if (kill == null) {
            kill = 0;
            kills.put(name, kill);
        }
        return kill >= killCoolLimit;
    }
}
