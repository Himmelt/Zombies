package org.soraworld.zombies.task;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.soraworld.zombies.config.Config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SpawnTask extends BukkitRunnable {

    private final Config config;
    private HashMap<String, Long> cools = new HashMap<>();
    private final PotionEffect FIRE_RESISTANCE = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000, 1);

    public SpawnTask(Config config) {
        this.config = config;
    }

    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Long last = cools.get(player.getName());
            if (last == null) {
                last = System.currentTimeMillis();
                cools.put(player.getName(), last);
            }
            if (System.currentTimeMillis() - last < config.killCoolTime() * 1000) {
                spawnZombiesAround(player);
            } else {
                if (config.debug()) {
                    System.out.println("[Zombies] " + player.getName() + " restart cool down.");
                }
                cools.put(player.getName(), System.currentTimeMillis());
                config.clearKills(player.getName());
            }
        }
        Iterator<Map.Entry<String, Long>> it = cools.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            if (Bukkit.getPlayer(entry.getKey()) == null && System.currentTimeMillis() - entry.getValue() > config.killCoolTime() * 1000) {
                it.remove();
                config.clearKills(entry.getKey());
            }
        }
    }

    private void spawnZombiesAround(final Player player) {
        if (player.getGameMode() != GameMode.SURVIVAL
                || !config.isAllow(player.getWorld().getName())
                || config.killCool(player.getName()))
            return;
        int radius = config.maxSpawnRadius();
        List<Entity> entities = player.getNearbyEntities(radius, radius, radius);
        int count = 0;
        for (Entity entity : entities) {
            if (entity instanceof Zombie) count++;
        }
        if (count < config.spawnLimit()) {
            Location location = randomLocation(player);
            if (location != null) {
                double health = config.randHealth();
                double mutate = config.getBabyChance();
                Zombie zombie = player.getWorld().spawn(location, Zombie.class);
                zombie.setMaxHealth(health);
                zombie.setHealth(health * 0.99);
                zombie.setBaby(Math.random() < mutate);
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, config.randSpeed()), false);
                zombie.addPotionEffect(FIRE_RESISTANCE);
                zombie.setCanPickupItems(false);
                if (config.debug()) {
                    System.out.println("[Zombies] Spawning around " + player.getName());
                }
            }
        } else {
            if (config.debug()) {
                System.out.println("[Zombies] zombies' amount around " + player.getName() + " is up to limit.");
            }
        }
    }

    private Location randomLocation(Player player) {
        Location origin = player.getLocation();
        double radius = config.randSpawnRadius();
        double theta = Math.random() * 6.28318531D;
        int x = (int) (origin.getBlockX() + Math.sin(theta) * radius);
        int z = (int) (origin.getBlockZ() + Math.cos(theta) * radius);
        int y = getY(player.getWorld(), x, origin.getBlockY(), z, radius);
        if (y > 0 && y < 256) return new Location(player.getWorld(), x, y, z);
        return null;
    }

    private int getY(World world, int x, int y, int z, double radius) {
        Block block;
        for (int i = 0; i <= radius && y + i < 255 && y - i > 1; i++) {
            block = world.getBlockAt(x, y + i, z);
            if (block.getType().isSolid()) {
                block = world.getBlockAt(x, y + i + 1, z);
                if (block.getType().isTransparent()) {
                    block = world.getBlockAt(x, y + i + 2, z);
                    if (block.getType().isTransparent()) return y + i + 1;
                }
            }
            block = world.getBlockAt(x, y - i, z);
            if (block.getType().isTransparent()) {
                block = world.getBlockAt(x, y - i - 1, z);
                if (block.getType().isTransparent()) {
                    block = world.getBlockAt(x, y - i - 2, z);
                    if (block.getType().isSolid()) return y - i - 1;
                }
            }
        }
        return -1;
    }

}
