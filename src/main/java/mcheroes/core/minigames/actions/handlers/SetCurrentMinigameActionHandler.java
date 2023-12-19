package mcheroes.core.minigames.actions.handlers;

import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.api.minigame.Minigame;
import mcheroes.core.minigames.MinigameManager;
import mcheroes.core.minigames.actions.SetCurrentMinigameAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetCurrentMinigameActionHandler implements ActionHandler<SetCurrentMinigameAction, Minigame> {
    private final MinigameManager manager;

    public SetCurrentMinigameActionHandler(MinigameManager manager) {
        this.manager = manager;
    }

    @Override
    public Minigame handle(SetCurrentMinigameAction action) {
        final Minigame minigame = action.minigame();
        if (minigame != null) {
            minigame.getIntroducer().setEndCallback(minigame::start);
            minigame.getIntroducer().start();
            for (Player player : Bukkit.getOnlinePlayers()) {
                minigame.handlePreGame(player);
            }
        }
        manager.setCurrentMinigame(minigame == null ? null : minigame.getId());

        return minigame;
    }
}
