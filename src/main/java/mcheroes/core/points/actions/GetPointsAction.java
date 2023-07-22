package mcheroes.core.points.actions;

import mcheroes.core.api.action.Action;

import java.util.Objects;
import java.util.UUID;

public record GetPointsAction(UUID player) implements Action {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetPointsAction that = (GetPointsAction) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    @Override
    public String toString() {
        return "GetPointsAction{" + "player=" + player + '}';
    }
}
