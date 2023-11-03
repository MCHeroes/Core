package mcheroes.core.minigames.utils;

import mcheroes.core.action.ActionManager;
import mcheroes.core.teams.utils.TeamUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;

public final class MinigamePlayerUtil {
    public static Set<Player> getPlayers(ActionManager actionManager) {
        return Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> TeamUtil.getTeam(actionManager, player) != null)
                .collect(Collectors.toSet());
    }
}
