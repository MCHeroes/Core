package mcheroes.core.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import mcheroes.core.action.ActionManager;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import mcheroes.core.teams.Team;
import mcheroes.core.teams.actions.GetTeamAction;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatFeature implements CoreFeature, Listener {
    private final LocaleAdapter locale;
    private final ActionManager actionManager;

    public ChatFeature(LocaleAdapter locale, ActionManager actionManager) {
        this.locale = locale;
        this.actionManager = actionManager;
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void on(AsyncChatEvent event) {
        event.setCancelled(true);

        final Component message = event.message();
        final Component name = event.getPlayer().displayName();
        final Team team = actionManager.run(new GetTeamAction(event.getPlayer().getUniqueId()));

        Bukkit.broadcast(Messages.CHAT_FORMAT.build(locale, team, name, message));
    }
}
