package mcheroes.core.points;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.HashCache;
import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.api.data.DataStore;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.points.actions.PointSetAction;
import mcheroes.core.utils.Scheduler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PointsFeature implements CoreFeature, Listener, ActionHandler<PointSetAction, Integer> {
    private final ActionManager actionManager;
    private final HashCache<UUID, Integer> cache;

    public PointsFeature(ActionManager actionManager, DataStore dataStore, Scheduler scheduler) {
        this.actionManager = actionManager;
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
        actionManager.register(PointSetAction.class, this);
    }

    @Override
    public void unload() {

    }

    @Override
    public Integer handle(PointSetAction action) {
        final Integer old = cache.get(action.player());
        cache.put(action.player(), action.to());

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
}
