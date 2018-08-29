package org.soraworld.zombies.flans;

import com.flansmod.common.guns.EntityBullet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.soraworld.zombies.manager.ZombiesManager;

import static org.soraworld.violet.nms.Version.*;

public class Flans {

    private static boolean support = false;

    public static void checkFlans(final ZombiesManager manager) {
        try {
            EntityBullet.class.getName();
            support = true;
            manager.consoleKey("flanSupport");
        } catch (Throwable ignored) {
            manager.consoleKey("flanNotSupport");
        }
    }

    public static Player getShooter(Entity entity) {
        if (support && entity != null) {
            if (v1_7_R4) {
                Object mce = ((org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_7_R4.EntityPlayer) {
                        return ((net.minecraft.server.v1_7_R4.EntityPlayer) bullet.owner).getBukkitEntity();
                    }
                }
            } else if (v1_8_R1) {
                Object mce = ((org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_8_R1.EntityPlayer) {
                        return ((net.minecraft.server.v1_8_R1.EntityPlayer) bullet.owner).getBukkitEntity();
                    }
                }
            } else if (v1_8_R3) {
                Object mce = ((org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_8_R3.EntityPlayer) {
                        return ((net.minecraft.server.v1_8_R3.EntityPlayer) bullet.owner).getBukkitEntity();
                    }
                }
            } else if (v1_9_R1) {
                Object mce = ((org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_9_R1.EntityPlayer) {
                        return ((net.minecraft.server.v1_9_R1.EntityPlayer) bullet.owner).getBukkitEntity();
                    }
                }
            } else if (v1_9_R2) {
                Object mce = ((org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_9_R2.EntityPlayer) {
                        return ((net.minecraft.server.v1_9_R2.EntityPlayer) bullet.owner).getBukkitEntity();
                    }
                }
            } else if (v1_10_R1) {
                Object mce = ((org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_10_R1.EntityPlayer) {
                        return ((net.minecraft.server.v1_10_R1.EntityPlayer) bullet.owner).getBukkitEntity();
                    }
                }
            } else if (v1_11_R1) {
                Object mce = ((org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_11_R1.EntityPlayer) {
                        return ((net.minecraft.server.v1_11_R1.EntityPlayer) bullet.owner).getBukkitEntity();
                    }
                }
            } else if (v1_12_R1) {
                Object mce = ((org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_12_R1.EntityPlayer) {
                        return ((net.minecraft.server.v1_12_R1.EntityPlayer) bullet.owner).getBukkitEntity();
                    }
                }
            } else if (v1_13_R1) {
                Object mce = ((org.bukkit.craftbukkit.v1_13_R1.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_13_R1.EntityPlayer) {
                        return ((net.minecraft.server.v1_13_R1.EntityPlayer) bullet.owner).getBukkitEntity();
                    }
                }
            } else if (v1_13_R2) {
                Object mce = ((org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity) entity).getHandle();
                //noinspection ConstantConditions
                if (mce instanceof EntityBullet) {
                    EntityBullet bullet = (EntityBullet) mce;
                    if (bullet.owner instanceof net.minecraft.server.v1_13_R2.EntityPlayer) {
                        return ((net.minecraft.server.v1_13_R2.EntityPlayer) bullet.owner).getBukkitEntity();
                    }
                }
            }
        }
        return null;
    }
}
