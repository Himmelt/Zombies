package org.soraworld.zombies.flans;

import org.bukkit.entity.Entity;
import org.soraworld.zombies.config.LangKeys;
import org.soraworld.zombies.util.ServerUtils;

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
            ServerUtils.console(LangKeys.format("flansSupport"));
        } catch (Throwable e) {
            ServerUtils.console(LangKeys.format("flansNotSupport"));
        }
    }

    private static Flans instance;

    public static Flans getInstance() {
        return instance == null ? instance = new Flans() : instance;
    }

    public String getShooter(Entity cause) {
        try {
            if (fieldEntity != null && cause != null) {
                Object source = fieldEntity.get(cause);
                if (source != null) {
                    if (EntityBullet != null && EntityBullet.isAssignableFrom(source.getClass())) {
                        if (owner != null) {
                            Object player = owner.get(source);
                            if (player != null && EntityPlayer != null && getName != null && EntityPlayer.isAssignableFrom(player.getClass())) {
                                return (String) getName.invoke(player);
                            }
                        }
                    } else if (EntityGrenade != null && EntityGrenade.isAssignableFrom(source.getClass())) {
                        if (thrower != null) {
                            Object player = thrower.get(source);
                            if (player != null && EntityPlayer != null && getName != null && EntityPlayer.isAssignableFrom(player.getClass())) {
                                return (String) getName.invoke(player);
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            //e.printStackTrace();
        }
        return "";
    }

}
