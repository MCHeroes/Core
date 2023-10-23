package mcheroes.core.minigames.actions;

import mcheroes.core.api.action.Action;
import mcheroes.core.api.minigame.Minigame;

import java.util.Objects;

public record RegisterMinigameAction(Minigame minigame) implements Action<String> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterMinigameAction that = (RegisterMinigameAction) o;
        return Objects.equals(minigame.getId(), that.minigame.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(minigame.getId());
    }

    @Override
    public String toString() {
        return "RegisterMinigameAction{" + "minigame=" + minigame.getId() + '}';
    }
}
