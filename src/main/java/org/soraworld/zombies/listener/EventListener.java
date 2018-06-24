package org.soraworld.zombies.listener;

import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.soraworld.zombies.config.ZombiesManager;
import org.soraworld.zombies.flans.Flans;
import rikka.RikkaAPI;
import rikka.api.entity.IEntity;
import rikka.api.entity.api.ProjectileSource;
import rikka.api.entity.living.IPlayer;
import rikka.api.entity.living.monster.IZombie;
import rikka.api.entity.projectile.IProjectile;

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
        IEntity damager = RikkaAPI.getEntity(event.getDamager());
        IEntity damagee = RikkaAPI.getEntity(event.getEntity());
        if (damagee instanceof IZombie) {
            IZombie zombie = (IZombie) damagee;
            if (zombie.health() - event.getDamage() > 0.0D) return;
            if (damager instanceof IPlayer) {
                IPlayer player = (IPlayer) damager;
                manager.addKill(player.getName());
            } else if (damager instanceof IProjectile) {
                ProjectileSource source = ((IProjectile) damager).getShooter();
                if (source instanceof IPlayer) manager.addKill(((IPlayer) source).getName());
            } else {
                IPlayer shooter = Flans.getShooter(damager);
                if (shooter != null) manager.addKill(shooter.getName());
            }
        }
    }

}
