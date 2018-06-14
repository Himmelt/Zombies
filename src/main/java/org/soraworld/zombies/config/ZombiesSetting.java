package org.soraworld.zombies.config;

import org.soraworld.violet.config.VioletSetting;

import java.util.HashSet;

public class ZombiesSetting extends VioletSetting {

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

}
