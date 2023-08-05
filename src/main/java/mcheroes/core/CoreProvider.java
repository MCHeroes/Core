package mcheroes.core;

import org.bukkit.Bukkit;

public class CoreProvider {
    private static final String PLUGIN_NAME = "MCHeroes";

    public static CorePlugin get() {
        if (!Bukkit.getPluginManager().isPluginEnabled(PLUGIN_NAME)) {
            throw new RuntimeException("MCHeroes core is not ready yet!");
        }

        return (CorePlugin) Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
    }
}
