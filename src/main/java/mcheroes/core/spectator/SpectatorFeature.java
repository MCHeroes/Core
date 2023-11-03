package mcheroes.core.spectator;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.spectator.actions.SetSpectatorAction;
import mcheroes.core.spectator.actions.UnsetSpectatorAction;
import mcheroes.core.spectator.actions.handlers.SetSpectatorActionHandler;
import mcheroes.core.spectator.actions.handlers.UnsetSpectatorActionHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SpectatorFeature implements CoreFeature, Listener {
    private final Set<UUID> tracked = new HashSet<>();
    private final ActionManager actionManager;

    public SpectatorFeature(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    @Override
    public void load() {
        actionManager.set(SetSpectatorAction.class, new SetSpectatorActionHandler(tracked, actionManager));
        actionManager.set(UnsetSpectatorAction.class, new UnsetSpectatorActionHandler(tracked));
    }

    @Override
    public void unload() {

    }

    @EventHandler
    public void on(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && tracked.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player && tracked.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player player && tracked.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
