package mcheroes.core.points.actions;

import mcheroes.core.api.action.Action;

import java.util.Objects;
import java.util.UUID;

public record SetPointsAction(UUID player, int to) implements Action<Integer> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetPointsAction that = (SetPointsAction) o;
        return to == that.to && Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, to);
    }

    @Override
    public String toString() {
        return "SetPointsAction{" + "player=" + player + ", to=" + to + '}';
    }
}
