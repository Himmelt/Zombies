package org.soraworld.zombies.flans;

import com.flansmod.common.guns.EntityBullet;
import org.soraworld.zombies.config.ZombiesManager;
import rikka.RikkaAPI;
import rikka.api.entity.IEntity;
import rikka.api.entity.living.IPlayer;
import rikka.bukkit.entity.BukkitEntity;
import rikka.sponge.entity.SpongeEntity;

import static rikka.RikkaAPI.BUKKIT;
import static rikka.RikkaAPI.SPONGE;
import static rikka.bukkit.server.NMSVersion.*;

public class Flans {

    private static boolean support = false;

    public static void checkFlans(final ZombiesManager manager) {
        try {
            EntityBullet.class.getName();
            support = true;
            manager.console("flanSupport");
        } catch (Throwable ignored) {
            manager.console("flanNotSupport");
        }
    }

    public static IPlayer getShooter(org.bukkit.entity.Entity entity) {
        if (support && entity != null) {
            if (v1_7_R4) {
                Object mce = ((org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_7_R4.EntityPlayer) {
                        return RikkaAPI.getPlayer(((net.minecraft.server.v1_7_R4.EntityPlayer) bullet.owner).getBukkitEntity());
                    }
                }
            } else if (v1_10_R1) {
                Object mce = ((org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_10_R1.EntityPlayer) {
                        return RikkaAPI.getPlayer(((net.minecraft.server.v1_10_R1.EntityPlayer) bullet.owner).getBukkitEntity());
                    }
                }
            } else if (v1_11_R1) {
                Object mce = ((org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_11_R1.EntityPlayer) {
                        return RikkaAPI.getPlayer(((net.minecraft.server.v1_11_R1.EntityPlayer) bullet.owner).getBukkitEntity());
                    }
                }
            } else if (v1_12_R1) {
                Object mce = ((org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_12_R1.EntityPlayer) {
                        return RikkaAPI.getPlayer(((net.minecraft.server.v1_12_R1.EntityPlayer) bullet.owner).getBukkitEntity());
                    }
                }
            }
        }
        return null;
    }

    public static IPlayer getShooter(org.spongepowered.api.entity.Entity entity) {
        if (support && entity != null) {
            if (entity instanceof EntityBullet) {
                EntityBullet bullet = (EntityBullet) entity;
                if (bullet.owner instanceof org.spongepowered.api.entity.living.player.Player) {
                    return RikkaAPI.getPlayer((org.spongepowered.api.entity.living.player.Player) bullet.owner);
                }
            }
        }
        return null;
    }

    public static IPlayer getShooter(IEntity entity) {
        if (support && BUKKIT && entity instanceof BukkitEntity) {
            return getShooter(((BukkitEntity) entity).getSource());
        }
        if (support && SPONGE && entity instanceof SpongeEntity) {
            return getShooter(((SpongeEntity) entity).getSource());
        }
        return null;
    }

}
