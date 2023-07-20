package mcheroes.core.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatFeature implements CoreFeature, Listener {
    private final LocaleAdapter locale;

    public ChatFeature(LocaleAdapter locale) {
        this.locale = locale;
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

        Bukkit.broadcast(Messages.CHAT_FORMAT.build(locale, name, message));
    }
}
