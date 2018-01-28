package org.soraworld.zombies.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.soraworld.zombies.util.FileUtils;
import org.soraworld.zombies.util.ServerUtils;

import java.io.File;
import java.io.InputStream;

public class LangKeys {

    private File file;
    private String lang;
    private final File folder;
    private final YamlConfiguration config = new YamlConfiguration();

    private static LangKeys instance;

    public LangKeys(File folder) {
        this.folder = folder;
        this.lang = "en_us";
        this.file = new File(folder, "en_us.yml");
        instance = this;
    }

    public void setLang(String lang) {
        this.lang = lang;
        this.file = new File(folder, lang + ".yml");
        load();
    }

    private void load() {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                InputStream input = this.getClass().getResourceAsStream("/lang/" + lang + ".yml");
                FileUtils.copyInputStreamToFile(input, file);
            } catch (Throwable e) {
                ServerUtils.console("lang file release exception !!!");
            }
        }
        try {
            config.load(file);
        } catch (Throwable e) {
            ServerUtils.console("lang file load exception !!!");
        }
    }

    public static String format(String key, Object... args) {
        if (instance == null) {
            return String.format(key, args);
        }
        String value = instance.config.getString(key);
        if (value == null) return String.format(key, args);
        return String.format(value.replace('&', ChatColor.COLOR_CHAR), args);
    }

}
