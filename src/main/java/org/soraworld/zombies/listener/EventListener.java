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
import org.soraworld.zombies.flans.Flans;
import org.soraworld.zombies.manager.ZombiesManager;

public class EventListener implements Listener {

    private final ZombiesManager manager;

    public EventListener(ZombiesManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (manager.customDrops && event.getEntity() instanceof Zombie) {
            event.setDroppedExp(manager.randDropExp());
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
                Player player = (Player) damager;
                manager.addKill(player.getName());
            } else if (damager instanceof Projectile) {
                ProjectileSource source = ((Projectile) damager).getShooter();
                if (source instanceof Player) manager.addKill(((Player) source).getName());
            } else {
                Player shooter = Flans.getShooter(damager);
                if (shooter != null) manager.addKill(shooter.getName());
            }
        }
    }
}
