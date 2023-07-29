package mcheroes.core.minigames.actions.handlers;

import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.minigames.MinigameRegistry;
import mcheroes.core.minigames.actions.RegisterMinigameAction;

public class RegisterMinigameActionHandler implements ActionHandler<RegisterMinigameAction, String> {
    private final MinigameRegistry registry;

    public RegisterMinigameActionHandler(MinigameRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String handle(RegisterMinigameAction action) {
        registry.register(action.minigame());

        return action.minigame().getId();
    }
}
