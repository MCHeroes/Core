package mcheroes.core;

import mcheroes.core.action.ActionManager;
import mcheroes.core.points.actions.GetPointsAction;
import mcheroes.core.teams.Team;
import mcheroes.core.teams.actions.GetTeamAction;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redempt.redlib.misc.FormatUtils;

public class CorePlaceholders extends PlaceholderExpansion {
    private final ActionManager actionManager;

    public CorePlaceholders(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "core";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MCHeroes";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return null;

        return switch (params) {
            case "points" -> {
                final Integer points = actionManager.run(new GetPointsAction(player.getUniqueId()));
                if (points == null) yield null;

                yield FormatUtils.formatLargeInteger(points);
            }
            case "team" -> {
                final Team team = actionManager.run(new GetTeamAction(player.getUniqueId()));
                if (team == null) yield null;

                yield team.name();
            }
            case "team_color" -> {
                final Team team = actionManager.run(new GetTeamAction(player.getUniqueId()));
                if (team == null) yield null;

                yield LegacyComponentSerializer.legacySection().serialize(Component.empty().color(team.color()));
            }
            default -> null;
        };
    }
}
