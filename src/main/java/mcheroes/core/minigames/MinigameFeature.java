package mcheroes.core.minigames;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import mcheroes.core.minigames.actions.*;
import mcheroes.core.minigames.actions.handlers.*;
import mcheroes.core.utils.Permissions;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class MinigameFeature implements CoreFeature {
    private final MinigameRegistry registry = new MinigameRegistry();
    private final MinigameManager manager = new MinigameManager();
    private final ActionManager actionManager;
    private final GameManagerGUI gameManagerGUI;
    private final BukkitCommandHandler commandHandler;
    private final LocaleAdapter locale;

    public MinigameFeature(ActionManager actionManager, LocaleAdapter locale, BukkitCommandHandler commandHandler) {
        this.actionManager = actionManager;
        this.gameManagerGUI = new GameManagerGUI(locale, manager, registry);
        this.commandHandler = commandHandler;
        this.locale = locale;
    }

    @Override
    public void load() {
        commandHandler.register(this);

        actionManager.set(GetMinigameAction.class, new GetMinigameActionHandler(registry));
        actionManager.set(RegisterMinigameAction.class, new RegisterMinigameActionHandler(registry));
        actionManager.set(GetAllMinigamesAction.class, new GetAllMinigamesActionHandler(registry));
        actionManager.set(GetCurrentMinigameAction.class, new GetCurrentMinigameActionHandler(registry, manager));
        actionManager.set(SetCurrentMinigameAction.class, new SetCurrentMinigameActionHandler(manager));
    }

    @Override
    public void unload() {

    }

    @Command({"gamemanager", "admin"})
    public void onCommand(Player sender) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return;
        }

        gameManagerGUI.open(sender);
    }
}
