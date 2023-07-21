package mcheroes.core.hub;

import mcheroes.core.api.data.DataStore;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import mcheroes.core.utils.Permissions;
import mcheroes.core.utils.Position;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class HubFeature implements CoreFeature, Listener {
    private final BukkitCommandHandler commandHandler;
    private final DataStore dataStore;
    private final LocaleAdapter locale;
    private Location cachedLocation;

    public HubFeature(BukkitCommandHandler commandHandler, DataStore dataStore, LocaleAdapter locale) {
        this.commandHandler = commandHandler;
        this.dataStore = dataStore;
        this.locale = locale;
    }

    @Override
    public void load() {
        commandHandler.register(this);
        cachedLocation = dataStore.getHubSpawn().toBukkit();
    }

    @Override
    public void unload() {

    }

    @EventHandler
    public void on(PlayerSpawnLocationEvent event) {
        event.setSpawnLocation(cachedLocation);
    }

    @Command("hub")
    public void onHub(Player sender) {
        sender.teleport(cachedLocation);
    }

    @Command("sethub")
    public void onSetHub(Player sender) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return;
        }

        cachedLocation = sender.getLocation();
        dataStore.setHubSpawn(Position.of(cachedLocation));
        sender.sendMessage(Messages.HUB_SET_SUCCESSFULLY.build(locale));
    }
}
