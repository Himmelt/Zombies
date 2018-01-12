package org.soraworld.zombies.flans;

import org.bukkit.entity.Entity;

import java.lang.reflect.Field;

public class Flans {

    private Class CraftEntity;
    private Class EntityBullet;
    private Class EntityGrenade;
    private Class EntityPlayer;
    private Field fieldEntity;
    private Field owner;
    private Field thrower;

    private Flans() {
        System.out.println("_________________new__________________");
        try {
            CraftEntity = Class.forName("org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity");
            EntityBullet = Class.forName("com.flansmod.common.guns.EntityBullet");
            EntityGrenade = Class.forName("com.flansmod.common.guns.EntityGrenade");
            EntityPlayer = Class.forName("net.minecraft.entity.player.EntityPlayer");
            fieldEntity = CraftEntity.getDeclaredField("entity");
            fieldEntity.setAccessible(true);
            owner = EntityBullet.getDeclaredField("owner");
            owner.setAccessible(true);
            thrower = EntityGrenade.getDeclaredField("thrower");
            thrower.setAccessible(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("_________________mid__________________");
        System.out.println(CraftEntity);
        System.out.println(EntityBullet);
        System.out.println(EntityGrenade);
        System.out.println(EntityPlayer);
        System.out.println(fieldEntity);
        System.out.println(owner);
        System.out.println(thrower);
        System.out.println("_________________end__________________");
    }

    private static Flans instance;

    public static Flans getInstance() {
        return instance == null ? instance = new Flans() : instance;
    }

    public String getBulletShooter(Entity bullet) {
        try {
            if (fieldEntity != null && bullet != null) {
                System.out.println(bullet.getClass());
                Object object = fieldEntity.get(bullet);
                if (object != null) {
                    System.out.println(object.getClass());
                    if (owner != null) {
                        Object player = owner.get(object);
                        if (player != null) {
                            System.out.println(player.getClass());
                            System.out.println(player);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("*******************************************8");
        return "null";
    }
}
