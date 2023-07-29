package mcheroes.core.minigames;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.minigames.actions.GetAllMinigamesAction;
import mcheroes.core.minigames.actions.GetMinigameAction;
import mcheroes.core.minigames.actions.RegisterMinigameAction;
import mcheroes.core.minigames.actions.handlers.GetAllMinigamesActionHandler;
import mcheroes.core.minigames.actions.handlers.GetMinigameActionHandler;
import mcheroes.core.minigames.actions.handlers.RegisterMinigameActionHandler;

public class MinigameFeature implements CoreFeature {
    private final MinigameRegistry registry = new MinigameRegistry();
    private final ActionManager actionManager;

    public MinigameFeature(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    @Override
    public void load() {
        actionManager.set(GetMinigameAction.class, new GetMinigameActionHandler(registry));
        actionManager.set(RegisterMinigameAction.class, new RegisterMinigameActionHandler(registry));
        actionManager.set(GetAllMinigamesAction.class, new GetAllMinigamesActionHandler(registry));
    }

    @Override
    public void unload() {

    }
}
