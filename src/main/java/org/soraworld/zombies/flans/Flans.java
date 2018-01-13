package org.soraworld.zombies.flans;

import org.bukkit.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Flans {

    private Class CraftEntity;
    private Class EntityBullet;
    private Class EntityGrenade;
    private Class EntityPlayer;
    private Field fieldEntity;
    private Field owner;
    private Field thrower;
    private Method getName;

    private Flans() {
        System.out.println("[Zombies] get flans mod support!");
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
            //net.minecraft.command.ICommandSender#getCommandSenderName
            getName = EntityPlayer.getMethod("func_70005_c_");
            getName.setAccessible(true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static Flans instance;

    public static Flans getInstance() {
        return instance == null ? instance = new Flans() : instance;
    }

    public String getBulletShooter(Entity cause) {
        try {
            if (fieldEntity != null && cause != null) {
                Object bullet = fieldEntity.get(cause);
                if (bullet != null) {
                    if (owner != null) {
                        Object player = owner.get(bullet);
                        if (player != null && EntityPlayer != null && EntityPlayer.isAssignableFrom(player.getClass())) {
                            if (getName != null) {
                                return (String) getName.invoke(player);
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }
}