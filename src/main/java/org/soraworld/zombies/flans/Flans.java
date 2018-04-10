package org.soraworld.zombies.flans;

import com.flansmod.common.guns.EntityBullet;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.soraworld.zombies.config.Config;

public class Flans {

    private static boolean support = false;

    public static void checkFlans(final Config config) {
        try {
            CraftEntity.class.getName();
            net.minecraft.server.v1_7_R4.Entity.class.getName();
            EntityBullet.class.getName();
            support = true;
            config.console("flanSupport");
        } catch (Throwable ignored) {
            config.console("flanNotSupport");
        }
    }

    public static Player getShooter(Entity craftBullet) {
        if (support && craftBullet instanceof CraftEntity) {
            net.minecraft.server.v1_7_R4.Entity entity = ((CraftEntity) craftBullet).getHandle();
            if (entity instanceof EntityBullet) {
                EntityBullet bullet = (EntityBullet) entity;
                if (bullet.owner != null) {
                    CraftEntity player = bullet.owner.getBukkitEntity();
                    if (player instanceof Player) {
                        return (Player) player;
                    }
                }
            }
        }
        return null;
    }

}
