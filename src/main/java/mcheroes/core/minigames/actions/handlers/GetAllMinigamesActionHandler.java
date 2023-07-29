package mcheroes.core.minigames.actions.handlers;

import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.api.minigame.Minigame;
import mcheroes.core.minigames.MinigameRegistry;
import mcheroes.core.minigames.actions.GetAllMinigamesAction;

import java.util.HashSet;
import java.util.Set;

public class GetAllMinigamesActionHandler implements ActionHandler<GetAllMinigamesAction, Set<Minigame>> {
    private final MinigameRegistry registry;

    public GetAllMinigamesActionHandler(MinigameRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Set<Minigame> handle(GetAllMinigamesAction action) {
        return new HashSet<>(registry.getMinigames().values());
    }
}
