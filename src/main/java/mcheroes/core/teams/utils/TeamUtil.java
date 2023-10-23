package mcheroes.core.teams.utils;

import mcheroes.core.action.ActionManager;
import mcheroes.core.teams.Team;
import mcheroes.core.teams.actions.GetTeamAction;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class TeamUtil {
    @Nullable
    public static Team getTeam(ActionManager actionManager, Player player) {
        return actionManager.run(new GetTeamAction(player.getUniqueId()));
    }

    @Nullable
    public static Team getTeam(ActionManager actionManager, UUID player) {
        return actionManager.run(new GetTeamAction(player));
    }
}
