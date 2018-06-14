package org.soraworld.zombies.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.soraworld.violet.config.VioletSetting;

import java.util.HashSet;

@ConfigSerializable
public class ZombiesSetting extends VioletSetting {

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

}
