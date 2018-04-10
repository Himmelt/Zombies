package org.soraworld.zombies.listener;

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
        if (event.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getEntity();

            if (zombie.getHealth() - event.getDamage() > 0.0D) return;

            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                config.addKill(player.getName());
            } else if (event.getDamager() instanceof Projectile) {
                ProjectileSource source = ((Projectile) event.getDamager()).getShooter();
                if (source instanceof Player) {
                    config.addKill(((Player) source).getName());
                }
            } else {
                try {
                    Player shooter = Flans.getShooter(event.getDamager());
                    if (shooter != null) config.addKill(shooter.getName());
                } catch (Throwable e) {
                    if (config.debug()) e.printStackTrace();
                }
            }
        }
    }

}
