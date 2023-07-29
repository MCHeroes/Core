package mcheroes.core.minigames.actions.handlers;

import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.api.minigame.Minigame;
import mcheroes.core.minigames.MinigameManager;
import mcheroes.core.minigames.MinigameRegistry;
import mcheroes.core.minigames.actions.GetCurrentMinigameAction;

public class GetCurrentMinigameActionHandler implements ActionHandler<GetCurrentMinigameAction, Minigame> {
    private final MinigameRegistry registry;
    private final MinigameManager manager;

    public GetCurrentMinigameActionHandler(MinigameRegistry registry, MinigameManager manager) {
        this.registry = registry;
        this.manager = manager;
    }

    @Override
    public Minigame handle(GetCurrentMinigameAction action) {
        if (manager.getCurrentMinigame() == null) return null;

        return registry.get(manager.getCurrentMinigame());
    }
}
