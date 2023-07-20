package mcheroes.core;

import mcheroes.core.api.data.DataStore;
import mcheroes.core.api.data.impl.JSONDataStore;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.chat.ChatFeature;
import mcheroes.core.hub.HubFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.points.PointsFeature;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class CorePlugin extends JavaPlugin {
    private final Set<CoreFeature> loadedFeatures = new HashSet<>();

    private BukkitCommandHandler commandHandler;
    private LocaleAdapter locale;
    private DataStore dataStore;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupLocale();
        setupDataStore();
        setupCommands();

        getLogger().info("Initializing core features...");
        initializeFeatures();

        commandHandler.registerBrigadier();

        getLogger().info("MCHeroes event core is ready to go!");
    }

    @Override
    public void onDisable() {
        commandHandler.unregisterAllCommands();
        for (CoreFeature feature : loadedFeatures) {
            feature.unload();
        }
        loadedFeatures.clear();

        try {
            dataStore.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getLogger().info("MCHeroes event core has successfully stopped.");
    }

    public LocaleAdapter getLocale() {
        return locale;
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    private void setupDataStore() {
        dataStore = new JSONDataStore(getDataFolder().toPath().resolve("data.json"));
        dataStore.load();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, dataStore::save, 0, 20L);
    }

    private void setupCommands() {
        commandHandler = BukkitCommandHandler.create(this);
        commandHandler.enableAdventure();
    }

    private void setupLocale() {
        final File file = new File(getDataFolder(), "messages.yml");
        if (!file.exists()) {
            saveResource("messages.yml", true);
        }

        locale = new LocaleAdapter(file, getTextResource("messages.yml"));
    }

    private void initializeFeatures() {
        loadedFeatures.add(new PointsFeature());
        loadedFeatures.add(new ChatFeature(locale));
        loadedFeatures.add(new HubFeature(commandHandler, dataStore, locale));

        for (CoreFeature feature : loadedFeatures) {
            if (feature instanceof Listener listener) getServer().getPluginManager().registerEvents(listener, this);

            feature.load();
        }
    }
}
