package mcheroes.core.spectator.utills;

import mcheroes.core.CorePlugin;
import mcheroes.core.CoreProvider;
import mcheroes.core.action.ActionManager;
import mcheroes.core.minigames.utils.MinigamePlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;

public final class VisibilityUtil {
  public static void hide(Player spectator, ActionManager actionManager) {
    final CorePlugin plugin = CoreProvider.get();

    // Fix any inconsistencies
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      onlinePlayer.showPlayer(plugin, spectator);
      spectator.showPlayer(plugin, onlinePlayer);
    }

    final Set<Player> players = MinigamePlayerUtil.getPlayers(actionManager);
    for (Player otherAlive : players) {
      otherAlive.hidePlayer(plugin, spectator);
      spectator.showPlayer(plugin, otherAlive);
    }
  }

  public static void reset(Player spectator) {
    final CorePlugin plugin = CoreProvider.get();
    for (Player other : Bukkit.getOnlinePlayers()) {
      other.showPlayer(plugin, spectator);
      spectator.showPlayer(plugin, other);
    }
  }
}
