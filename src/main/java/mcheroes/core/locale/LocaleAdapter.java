package mcheroes.core.locale;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

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

    public Component get(String key) {
        return LocaleParser.parse(config.getString(key, "MessageNotFound:" + key));
    }

    public Component getArgs(String key, Object... args) {
        return LocaleParser.parse(config.getString(key, "MessageNotFound:" + key), args);
    }

    public List<Component> getSplit(String key) {
        return LocaleParser.parse(config.getStringList(key));
    }

    public List<Component> getSplitArgs(String key, Object... args) {
        return LocaleParser.parse(config.getStringList(key), args);
    }
}
