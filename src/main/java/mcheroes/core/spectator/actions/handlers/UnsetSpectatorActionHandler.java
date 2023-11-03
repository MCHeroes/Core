package mcheroes.core.spectator.actions.handlers;

import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.spectator.actions.UnsetSpectatorAction;
import mcheroes.core.spectator.utills.VisibilityUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class UnsetSpectatorActionHandler implements ActionHandler<UnsetSpectatorAction, Boolean> {
    private final Set<UUID> tracked;

    public UnsetSpectatorActionHandler(Set<UUID> tracked) {
        this.tracked = tracked;
    }

    @Override
    public Boolean handle(UnsetSpectatorAction action) {
        final Player player = Bukkit.getPlayer(action.player());
        if (player != null) {
            player.getInventory().clear();
            player.setAllowFlight(false);
            player.setFlying(false);
            VisibilityUtil.reset(player);
        }
        return tracked.remove(action.player());
    }
}
