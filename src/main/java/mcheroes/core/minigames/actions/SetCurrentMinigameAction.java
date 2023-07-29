package mcheroes.core.minigames.actions;

import mcheroes.core.api.action.Action;
import mcheroes.core.api.minigame.Minigame;

import java.util.Objects;

public record SetCurrentMinigameAction(Minigame minigame) implements Action {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetCurrentMinigameAction that = (SetCurrentMinigameAction) o;
        return Objects.equals(minigame.getId(), that.minigame.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(minigame.getId());
    }

    @Override
    public String toString() {
        return "SetCurrentMinigameAction{" + "minigame=" + minigame.getId() + '}';
    }
}
