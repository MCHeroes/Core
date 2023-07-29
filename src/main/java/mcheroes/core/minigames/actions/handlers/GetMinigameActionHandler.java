package mcheroes.core.minigames.actions.handlers;

import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.api.minigame.Minigame;
import mcheroes.core.minigames.MinigameRegistry;
import mcheroes.core.minigames.actions.GetMinigameAction;

public class GetMinigameActionHandler implements ActionHandler<GetMinigameAction, Minigame> {
    private final MinigameRegistry registry;

    public GetMinigameActionHandler(MinigameRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Minigame handle(GetMinigameAction action) {
        return registry.get(action.id());
    }
}
