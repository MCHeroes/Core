package mcheroes.core;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.data.DataStore;
import mcheroes.core.api.data.impl.JSONDataStore;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.chat.ChatFeature;
import mcheroes.core.hub.HubFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.minigames.MinigameFeature;
import mcheroes.core.points.PointsFeature;
import mcheroes.core.teams.TeamsFeature;
import mcheroes.core.utils.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class CorePlugin extends JavaPlugin {
    private final Set<CoreFeature> loadedFeatures = new HashSet<>();
    private final ActionManager actionManager = new ActionManager();

    private BukkitCommandHandler commandHandler;
    private LocaleAdapter locale;
    private DataStore dataStore;
    private Scheduler scheduler;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupLocale();
        setupDataStore();
        setupCommands();

        scheduler = new Scheduler(this);

        getLogger().info("Initializing core features...");
        initializeFeatures();

        commandHandler.registerBrigadier();

        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new CorePlaceholders(actionManager).register();
        }

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

    public ActionManager getActionManager() {
        return actionManager;
    }

    public BukkitCommandHandler getCommandHandler() {
        return commandHandler;
    }

    public Set<CoreFeature> getLoadedFeatures() {
        return loadedFeatures;
    }

    private void setupDataStore() {
        dataStore = new JSONDataStore(getDataFolder().toPath().resolve("data.json"));
        dataStore.load();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, dataStore::save, 0, 20L);
    }

    private void setupCommands() {
        commandHandler = BukkitCommandHandler.create(this);
        commandHandler.enableAdventure();
        commandHandler.getAutoCompleter().registerParameterSuggestions(OfflinePlayer.class, SuggestionProvider.of(() -> Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).collect(Collectors.toList())));
        commandHandler.register(new CoreCommands(this));
    }

    private File getConfigFile(String id) {
        final File file = new File(getDataFolder(), id + ".yml");
        if (!file.exists()) {
            saveResource(id + ".yml", true);
        }

        return file;
    }

    private void setupLocale() {
        final File file = getConfigFile("messages");

        locale = new LocaleAdapter(file, getTextResource("messages.yml"));
    }

    private void initializeFeatures() {
        // No guaranteed load order. Do not attempt to communicate data between features or depend on another feature! Features need to be as separated as possible.
        loadedFeatures.add(new PointsFeature(actionManager, dataStore, scheduler, commandHandler, locale));
        loadedFeatures.add(new ChatFeature(locale, actionManager));
        loadedFeatures.add(new HubFeature(commandHandler, dataStore, locale, scheduler));
        loadedFeatures.add(new TeamsFeature(getConfigFile("teams"), commandHandler, locale, actionManager));
        loadedFeatures.add(new MinigameFeature(actionManager, locale, commandHandler));

        for (CoreFeature feature : loadedFeatures) {
            if (feature instanceof Listener listener) getServer().getPluginManager().registerEvents(listener, this);

            try {
                feature.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
