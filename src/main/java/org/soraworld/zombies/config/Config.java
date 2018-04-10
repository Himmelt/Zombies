package org.soraworld.zombies.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.soraworld.violet.config.IIConfig;
import org.soraworld.zombies.flans.Flans;
import org.soraworld.zombies.scoreboard.ScoreBoards;
import org.soraworld.zombies.util.ServerUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Config extends IIConfig {

    private String lang = "en_us";
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
    private boolean debug = false;
    private final HashSet<String> allowWorlds = new HashSet<>();

    private final File file;
    private final LangKeys langKeys;
    private final ScoreBoards killsBoard = new ScoreBoards();
    private final HashMap<String, Integer> kills = new HashMap<>();
    private final YamlConfiguration config = new YamlConfiguration();

    public Config(File file) {
        this.file = new File(file, "config.yml");
        this.langKeys = new LangKeys(new File(file, "lang"));
    }

    public void load() {
        if (!file.exists()) {
            if (lang == null || lang.isEmpty()) {
                lang = "en_us";
            }
            langKeys.setLang(lang);
            save();
            return;
        }
        try {
            config.load(file);
            lang = config.getString("lang");
            if (lang == null || lang.isEmpty()) {
                lang = "en_us";
            }
            langKeys.setLang(lang);
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
            refresh = config.getInt("refresh");
            spawnLimit = config.getInt("spawnLimit");
            killCoolLimit = config.getInt("killCoolLimit");
            killCoolTime = config.getInt("killCoolTime");
            babyChance = config.getDouble("babyChance");
            customDrops = config.getBoolean("customDrops");
            // dropExp
            String dropExp = config.getString("dropExp");
            if (dropExp != null && dropExp.matches("[0-9]+-[0-9]+")) {
                String[] ss = dropExp.split("-");
                minDropExp = Integer.valueOf(ss[0]);
                maxDropExp = Integer.valueOf(ss[1]);
            }
            debug = config.getBoolean("debug");
            displaySlot = config.getInt("displaySlot");
            allowWorlds.clear();
            allowWorlds.addAll(config.getStringList("allowWorlds"));

        } catch (Throwable e) {
            ServerUtils.console("config file load exception !!!");
        }
        if (displaySlot < 0 || displaySlot > 3) displaySlot = 0;
        killsBoard.setDisplaySlot(displaySlot);
        killsBoard.setDisplayName(LangKeys.format("scoreboardDisplay"));
    }

    public void save() {
        try {
            config.set("lang", lang);
            config.set("speed", minSpeed + "-" + maxSpeed);
            config.set("health", minHealth + "-" + maxHealth);
            config.set("spawnRadius", minSpawnRadius + "-" + maxSpawnRadius);
            config.set("refresh", refresh);
            config.set("spawnLimit", spawnLimit);
            config.set("killCoolLimit", killCoolLimit);
            config.set("killCoolTime", killCoolTime);
            config.set("babyChance", babyChance);
            config.set("customDrops", customDrops);
            config.set("dropExp", minDropExp + "-" + maxDropExp);
            config.set("displaySlot", displaySlot);
            config.set("debug", debug);
            config.set("allowWorlds", new ArrayList<>(allowWorlds));
            config.save(file);
        } catch (Throwable e) {
            ServerUtils.console("config file save exception !!!");
        }
    }

    public boolean customDrops() {
        return customDrops;
    }

    public void debug(boolean debug) {
        this.debug = debug;
    }

    protected void loadOptions() {

    }

    protected void saveOptions() {

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
        return null;
    }

    public String defaultAdminPerm() {
        return null;
    }

    public boolean debug() {
        return debug;
    }

    public int randSpeed() {
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
        return refresh;
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
        if (name == null || name.isEmpty()) return;
        Integer kill = kills.get(name);
        if (kill == null) kill = 0;
        kills.put(name, kill + 1);
        killsBoard.update(name, kill + 1);
        ServerUtils.debug(debug, LangKeys.format("debugKillCount", name, kill + 1));
    }

    public void displaySlot(int slot) {
        displaySlot = slot;
        if (displaySlot < 0 || displaySlot > 3) displaySlot = 0;
        killsBoard.setDisplaySlot(slot);
    }

    public boolean killCool(String name) {
        Integer kill = kills.get(name);
        if (kill >= killCoolLimit) {
            ServerUtils.debug(debug, LangKeys.format("debugKillCool", name, kill, killCoolLimit));
        }
        return kill >= killCoolLimit;
    }

    public int randDropExp() {
        return (int) (minDropExp + Math.abs(maxDropExp - minDropExp) * Math.random());
    }

    public int displaySlot() {
        return displaySlot;
    }

    public String lang() {
        return lang;
    }

    public void lang(String lang) {
        if (lang != null && !lang.isEmpty()) {
            this.lang = lang;
            langKeys.setLang(lang);
            killsBoard.setDisplayName(LangKeys.format("scoreboardDisplay"));
        }
    }
}
