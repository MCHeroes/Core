package mcheroes.core.minigames;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.minigames.actions.*;
import mcheroes.core.minigames.actions.handlers.*;

public class MinigameFeature implements CoreFeature {
    private final MinigameRegistry registry = new MinigameRegistry();
    private final MinigameManager manager = new MinigameManager();
    private final ActionManager actionManager;

    public MinigameFeature(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    @Override
    public void load() {
        actionManager.set(GetMinigameAction.class, new GetMinigameActionHandler(registry));
        actionManager.set(RegisterMinigameAction.class, new RegisterMinigameActionHandler(registry));
        actionManager.set(GetAllMinigamesAction.class, new GetAllMinigamesActionHandler(registry));
        actionManager.set(GetCurrentMinigameAction.class, new GetCurrentMinigameActionHandler(registry, manager));
        actionManager.set(SetCurrentMinigameAction.class, new SetCurrentMinigameActionHandler(manager));
    }

    @Override
    public void unload() {

    }
}
