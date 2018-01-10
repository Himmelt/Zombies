package org.soraworld.zombies.listener;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.soraworld.zombies.config.Config;

public class EventListener implements Listener {

    private final Config config;

    public EventListener(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity monster = event.getEntity();
        if (config.customDrop() && (monster instanceof Zombie || monster instanceof Giant)) {
            event.getDrops().clear();
            if (Math.random() >= 0.5D && config.drops().size() > 0) {
                ItemStack stack = config.drops().get((int) (Math.random() * (double) config.drops().size()));
                if (stack != null) {
                    event.getDrops().add(stack);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamaged(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Zombie || entity instanceof Giant) {
            Monster monster = (Monster) entity;
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                if (monster.getHealth() - event.getDamage() <= 0.0D) {
                    //this.addKiller(player);
                }
            } else if (event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) event.getDamager();
                ProjectileSource source = projectile.getShooter();
                if (source instanceof Player && monster.getHealth() - event.getDamage() <= 0.0D) {
                    //this.addKiller((Player) source);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        if ((entity instanceof Zombie || entity instanceof Giant) && event.getTarget() instanceof Player) {
            ((Monster) entity).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, config.speedDuration(), config.randSpeed()));
        }
    }
}
