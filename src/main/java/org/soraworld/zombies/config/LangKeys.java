package org.soraworld.zombies.config;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        try {
            Field options = YamlConfiguration.class.getDeclaredField("yamlOptions");
            options.setAccessible(true);
            Method setAllowUnicode = DumperOptions.class.getDeclaredMethod("setAllowUnicode", boolean.class);
            setAllowUnicode.setAccessible(true);
            Object opt = options.get(config);
            setAllowUnicode.invoke(opt, true);
            System.out.println("[Zombies] unicode support success.");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        instance = this;
    }

    public void setLang(String lang) {
        this.lang = lang;
        this.file = new File(folder, lang + ".yml");
        load();
    }

    public void load() {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                InputStream input = this.getClass().getResourceAsStream("/lang/" + lang + ".yml");
                FileUtils.copyInputStreamToFile(input, file);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        try {
            config.load(file);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static String format(String key, Object... args) {
        if (instance == null) {
            return key;
        }
        String value = instance.config.getString(key);
        if (value == null) return key;
        return String.format(value.replace("&", "§"), args);
    }

}
