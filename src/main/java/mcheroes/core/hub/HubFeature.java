package mcheroes.core.hub;

import mcheroes.core.api.data.DataStore;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import mcheroes.core.utils.Permissions;
import mcheroes.core.utils.Position;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class HubFeature implements CoreFeature {
    private final BukkitCommandHandler commandHandler;
    private final DataStore dataStore;
    private final LocaleAdapter locale;

    public HubFeature(BukkitCommandHandler commandHandler, DataStore dataStore, LocaleAdapter locale) {
        this.commandHandler = commandHandler;
        this.dataStore = dataStore;
        this.locale = locale;
    }

    @Override
    public void load() {
        commandHandler.register(this);
    }

    @Override
    public void unload() {

    }

    @Command("hub")
    public void onHub(Player sender) {
        sender.teleport(dataStore.getHubSpawn().toBukkit());
    }

    @Command("sethub")
    public void onSetHub(Player sender) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return;
        }

        dataStore.setHubSpawn(Position.of(sender.getLocation()));
        sender.sendMessage(Messages.HUB_SET_SUCCESSFULLY.build(locale));
    }
}
