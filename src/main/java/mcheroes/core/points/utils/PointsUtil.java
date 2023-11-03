package mcheroes.core.points.utils;

import mcheroes.core.action.ActionManager;
import mcheroes.core.points.actions.GetPointsAction;
import mcheroes.core.points.actions.SetPointsAction;

import java.util.UUID;

public final class PointsUtil {
    public static void give(UUID player, int points, ActionManager actionManager) {
        actionManager.run(new SetPointsAction(player, points + get(player, actionManager)));
    }

    public static void take(UUID player, int points, ActionManager actionManager) {
        actionManager.run(new SetPointsAction(player, get(player, actionManager) - points));
    }

    public static void set(UUID player, int points, ActionManager actionManager) {
        actionManager.run(new SetPointsAction(player, points));
    }

    public static int get(UUID player, ActionManager actionManager) {
        final Integer found = actionManager.run(new GetPointsAction(player));
        return found == null ? 0 : found;
    }
}
