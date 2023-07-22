package mcheroes.core.locale;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class LocaleAdapter {
    private final YamlConfiguration config;
    private final File configFile;

    public LocaleAdapter(File file, Reader def) {
        this.config = YamlConfiguration.loadConfiguration(file);
        this.configFile = file;
        config.addDefaults(YamlConfiguration.loadConfiguration(def));
        config.options().copyDefaults(true).parseComments(true);
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public Component get(@Nullable Player reference, String key) {
        return LocaleParser.parse(reference, config.getString(key, "MessageNotFound:" + key));
    }

    public Component getArgs(@Nullable Player reference, String key, Object... args) {
        return LocaleParser.parse(reference, config.getString(key, "MessageNotFound:" + key), args);
    }

    public List<Component> getSplit(@Nullable Player reference, String key) {
        return LocaleParser.parse(reference, config.getStringList(key));
    }

    public List<Component> getSplitArgs(@Nullable Player reference, String key, Object... args) {
        return LocaleParser.parse(reference, config.getStringList(key), args);
    }

    public Component get(String key) {
        return get(null, key);
    }

    public Component getArgs(String key, Object... args) {
        return getArgs(null, key, args);
    }

    public List<Component> getSplit(String key) {
        return getSplit(null, key);
    }

    public List<Component> getSplitArgs(String key, Object... args) {
        return getSplitArgs(null, key, args);
    }
}
