package mcheroes.core.spectator.actions;

import mcheroes.core.api.action.Action;

import java.util.Objects;
import java.util.UUID;

public record SetSpectatorAction(UUID player) implements Action<Boolean> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetSpectatorAction that = (SetSpectatorAction) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    @Override
    public String toString() {
        return "SetSpectatorAction{" +
                "player=" + player +
                '}';
    }
}
