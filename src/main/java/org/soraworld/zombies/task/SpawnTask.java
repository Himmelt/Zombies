package org.soraworld.zombies.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.soraworld.zombies.Zombies;
import org.soraworld.zombies.config.Config;

import java.util.List;

public class SpawnTask extends BukkitRunnable {

    private final Config config;

    public SpawnTask(Config config) {
        this.config = config;
    }

    public void run() {

        System.out.println("Running....");

        for (Player player : Bukkit.getOnlinePlayers()) {
            spawnZombiesAround(player);
        }

    }

    private void spawnZombiesAround(final Player player) {
        if (config.isAllow(player.getWorld().getName())) {
            new BukkitRunnable() {

                private final PotionEffect FIRE_RESISTANCE = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 1);

                @Override
                public void run() {
                    int radius = config.spawnRadius();
                    List<Entity> entities = player.getNearbyEntities(radius, radius, radius);
                    int count = 0;
                    for (Entity entity : entities) {
                        if (entity instanceof Zombie) count++;
                    }
                    if (count < config.maxAmount()) {
                        double health = config.randHealth();
                        double mutate = config.getBabyChance();
                        Zombie zombie = player.getWorld().spawn(player.getLocation(), Zombie.class);
                        zombie.setMaxHealth(health);
                        zombie.setHealth(health * 0.99);
                        zombie.setBaby(Math.random() < mutate);
                        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 600, config.randSpeed()), false);
                        zombie.addPotionEffect(FIRE_RESISTANCE);
                        System.out.println("Spawn....");
                    }
                }

                public Location randomLocation() {
                    Location origin = player.getLocation();
                    double radius = 5 + config.spawnRadius() * Math.random();
                    int x = (int) (origin.getBlockX() + Math.sin(radius));
                    int z = (int) (origin.getBlockZ() + Math.cos(radius));

                }
            }.runTask(Zombies.getInstance());
        }
    }

}
