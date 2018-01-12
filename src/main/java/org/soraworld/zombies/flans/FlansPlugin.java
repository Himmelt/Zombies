package org.soraworld.zombies.flans;

import org.bukkit.entity.Entity;

import java.lang.reflect.Field;

public class FlansPlugin {

    private static Class CraftEntity;
    private static Class EntityBullet;
    private static Class EntityGrenade;
    private static Class EntityPlayer;
    private static Field fieldEntity;
    private static Field owner;
    private static Field thrower;

    static {
        try {
            CraftEntity = Class.forName("org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity");
            EntityBullet = Class.forName("com.flansmod.common.guns.EntityBullet");
            EntityGrenade = Class.forName("com.flansmod.common.guns.EntityGrenade");
            EntityPlayer = Class.forName("net.minecraft.entity.EneityPlayer");
            fieldEntity = CraftEntity.getDeclaredField("entity");
            fieldEntity.setAccessible(true);
            owner = EntityBullet.getDeclaredField("owner");
            owner.setAccessible(true);
            thrower = EntityGrenade.getDeclaredField("thrower");
            thrower.setAccessible(true);
        } catch (ClassNotFoundException ignored) {
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static String getBulletShooter(Entity bullet) {
        try {
            if (fieldEntity != null) {
                Object object = fieldEntity.get(bullet);
                System.out.println(object.getClass());
                if (owner != null) {
                    Object player = owner.get(object);
                    System.out.println(player.getClass());
                    System.out.println(player);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "null";
    }
}
