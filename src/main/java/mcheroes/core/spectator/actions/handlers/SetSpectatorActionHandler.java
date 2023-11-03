package mcheroes.core.spectator.actions.handlers;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.spectator.actions.SetSpectatorAction;
import mcheroes.core.spectator.utills.VisibilityUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class SetSpectatorActionHandler implements ActionHandler<SetSpectatorAction, Boolean> {
    private final Set<UUID> tracked;
    private final ActionManager actionManager;

    public SetSpectatorActionHandler(Set<UUID> tracked, ActionManager actionManager) {
        this.tracked = tracked;
        this.actionManager = actionManager;
    }

    @Override
    public Boolean handle(SetSpectatorAction action) {
        final Player player = Bukkit.getPlayer(action.player());
        if (player != null) {
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(true);
            player.setFlying(true);
            VisibilityUtil.hide(player, actionManager);
        }
        return tracked.add(action.player());
    }
}
