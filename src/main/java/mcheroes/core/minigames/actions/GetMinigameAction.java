package mcheroes.core.minigames.actions;

import mcheroes.core.api.action.Action;

import java.util.Objects;

public record GetMinigameAction(String id) implements Action {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetMinigameAction that = (GetMinigameAction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GetMinigameAction{" + "id='" + id + '\'' + '}';
    }
}
