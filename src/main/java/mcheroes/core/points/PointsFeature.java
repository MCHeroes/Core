package mcheroes.core.points;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.HashCache;
import mcheroes.core.api.data.DataStore;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import mcheroes.core.points.actions.GetPointsAction;
import mcheroes.core.points.actions.SetPointsAction;
import mcheroes.core.points.actions.handlers.GetPointsActionHandler;
import mcheroes.core.points.actions.handlers.SetPointsActionHandler;
import mcheroes.core.utils.Permissions;
import mcheroes.core.utils.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import redempt.redlib.inventorygui.InventoryGUI;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.UUID;

@Command("points")
public class PointsFeature implements CoreFeature, Listener {
    private final ActionManager actionManager;
    private final HashCache<UUID, Integer> cache;
    private final BukkitCommandHandler commandHandler;
    private final LocaleAdapter locale;
    private final DataStore dataStore;
    private final Scheduler scheduler;

    public PointsFeature(ActionManager actionManager, DataStore dataStore, Scheduler scheduler, BukkitCommandHandler commandHandler, LocaleAdapter locale) {
        this.actionManager = actionManager;
        this.commandHandler = commandHandler;
        this.locale = locale;
        this.dataStore = dataStore;
        this.scheduler = scheduler;
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
    }

    @Override
    public void load() {
        actionManager.set(SetPointsAction.class, new SetPointsActionHandler(cache));
        actionManager.set(GetPointsAction.class, new GetPointsActionHandler(cache));

        commandHandler.register(this);

        for (UUID player : dataStore.getPlayers()) {
            scheduler.runAsync(() -> cache.load(player));
        }
    }

    @Override
    public void unload() {

    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        scheduler.runAsync(() -> cache.load(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();
        if (!cache.containsKey(uuid)) return;

        cache.save(uuid, cache.get(uuid));
    }

    @Subcommand({"gui", "leaderboard"})
    @DefaultFor("points")
    public void onGui(Player sender) {
        new PointsGUI(new InventoryGUI(Bukkit.createInventory(null, 54, Messages.POINTS_GUI_TITLE.build(locale))), locale, cache).open(sender);
    }

    @Subcommand("set")
    public void onSet(CommandSender sender, OfflinePlayer target, int set) {
        if (checkAdmin(sender)) return;

        actionManager.run(new SetPointsAction(target.getUniqueId(), set));
        sender.sendMessage(Messages.POINTS_ACTION_SUCCESS.build(locale));
    }

    @Subcommand("add")
    public void onAdd(CommandSender sender, OfflinePlayer target, int add) {
        if (checkAdmin(sender)) return;

        actionManager.run(new SetPointsAction(target.getUniqueId(), cache.get(target.getUniqueId()) + add));
        sender.sendMessage(Messages.POINTS_ACTION_SUCCESS.build(locale));
    }

    @Subcommand("remove")
    public void onRemove(CommandSender sender, OfflinePlayer target, int remove) {
        if (checkAdmin(sender)) return;

        actionManager.run(new SetPointsAction(target.getUniqueId(), cache.get(target.getUniqueId()) - remove));
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
            actionManager.run(new SetPointsAction(target, 0));
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
