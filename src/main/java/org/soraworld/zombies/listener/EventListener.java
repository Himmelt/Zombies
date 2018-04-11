package org.soraworld.zombies.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.soraworld.zombies.config.Config;
import org.soraworld.zombies.flans.Flans;

public class EventListener implements Listener {

    private final Config config;

    public EventListener(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (config.customDrops() && event.getEntity() instanceof Zombie) {
            event.setDroppedExp(config.randDropExp());
            event.getDrops().clear();
        }
    }

    @EventHandler
    public void onEntityDamaged(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity damagee = event.getEntity();
        if (damagee instanceof Zombie) {
            Zombie zombie = (Zombie) damagee;
            if (zombie.getHealth() - event.getDamage() > 0.0D) return;
            if (damager instanceof Player) {
                config.addKill(damager.getName());
            } else if (damager instanceof Projectile) {
                ProjectileSource source = ((Projectile) damager).getShooter();
                if (source instanceof Player) config.addKill(((Player) source).getName());
            } else {
                Player shooter = Flans.getShooter(damager);
                if (shooter != null) config.addKill(shooter.getName());
            }
        }
    }

}
