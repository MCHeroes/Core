package mcheroes.core.points;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.HashCache;
import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.api.data.DataStore;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import mcheroes.core.points.actions.PointSetAction;
import mcheroes.core.utils.Permissions;
import mcheroes.core.utils.Scheduler;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.UUID;

@Command("points")
public class PointsFeature implements CoreFeature, Listener, ActionHandler<PointSetAction, Integer> {
    private final ActionManager actionManager;
    private final HashCache<UUID, Integer> cache;
    private final BukkitCommandHandler commandHandler;
    private final LocaleAdapter locale;
    private final PointsGUI gui;
    private final DataStore dataStore;

    public PointsFeature(ActionManager actionManager, DataStore dataStore, Scheduler scheduler, BukkitCommandHandler commandHandler, LocaleAdapter locale) {
        this.actionManager = actionManager;
        this.commandHandler = commandHandler;
        this.locale = locale;
        this.dataStore = dataStore;
        this.cache = new HashCache<>() {
            @Override
            public Integer load(UUID uuid) {
                return dataStore.getPlayerPoints(uuid);
            }

            @Override
            public void save(UUID uuid, Integer points) {
                scheduler.runAsync(() -> dataStore.setPlayerPoints(uuid, points));
            }
        };
        this.gui = new PointsGUI(locale, cache);
    }

    @Override
    public void load() {
        actionManager.register(PointSetAction.class, this);
        commandHandler.register(this);

        for (UUID player : dataStore.getPlayers()) {
            cache.load(player);
        }

        gui.updatePoints();
    }

    @Override
    public void unload() {

    }

    @Override
    public Integer handle(PointSetAction action) {
        final Integer old = cache.get(action.player());
        final PointsChangeEvent event = new PointsChangeEvent(action.player(), old, action.to());
        if (event.call()) {
            return old;
        }

        cache.put(action.player(), event.getNewPoints());
        gui.updatePoints();

        return old;
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        cache.load(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();
        if (!cache.containsKey(uuid)) return;

        cache.save(uuid, cache.get(uuid));
    }

    @Subcommand("set")
    public void onSet(CommandSender sender, OfflinePlayer target, int set) {
        if (checkAdmin(sender)) return;

        actionManager.run(new PointSetAction(target.getUniqueId(), set));
        sender.sendMessage(Messages.POINTS_ACTION_SUCCESS.build(locale));
    }

    @Subcommand("add")
    public void onAdd(CommandSender sender, OfflinePlayer target, int add) {
        if (checkAdmin(sender)) return;

        actionManager.run(new PointSetAction(target.getUniqueId(), cache.get(target.getUniqueId()) + add));
        sender.sendMessage(Messages.POINTS_ACTION_SUCCESS.build(locale));
    }

    @Subcommand("remove")
    public void onRemove(CommandSender sender, OfflinePlayer target, int remove) {
        if (checkAdmin(sender)) return;

        actionManager.run(new PointSetAction(target.getUniqueId(), cache.get(target.getUniqueId()) - remove));
        sender.sendMessage(Messages.POINTS_ACTION_SUCCESS.build(locale));
    }

    @Subcommand("get")
    public void onGet(CommandSender sender, OfflinePlayer target) {
        sender.sendMessage(Messages.POINTS_GET.build(locale, target, cache.get(target.getUniqueId())));
    }

    @Subcommand("resetall")
    public void onResetAll(CommandSender sender) {
        if (checkAdmin(sender)) return;

        for (UUID target : dataStore.getPlayers()) {
            actionManager.run(new PointSetAction(target, 0));
        }
        sender.sendMessage(Messages.POINTS_ACTION_SUCCESS.build(locale));
    }

    private boolean checkAdmin(CommandSender sender) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return true;
        }
        return false;
    }
}
