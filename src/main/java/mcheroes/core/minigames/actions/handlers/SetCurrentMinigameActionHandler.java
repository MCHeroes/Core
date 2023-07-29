package mcheroes.core.minigames.actions.handlers;

import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.api.minigame.Minigame;
import mcheroes.core.minigames.MinigameManager;
import mcheroes.core.minigames.actions.SetCurrentMinigameAction;

public class SetCurrentMinigameActionHandler implements ActionHandler<SetCurrentMinigameAction, Minigame> {
    private final MinigameManager manager;

    public SetCurrentMinigameActionHandler(MinigameManager manager) {
        this.manager = manager;
    }

    @Override
    public Minigame handle(SetCurrentMinigameAction action) {
        manager.setCurrentMinigame(action.minigame().getId());

        return action.minigame();
    }
}
