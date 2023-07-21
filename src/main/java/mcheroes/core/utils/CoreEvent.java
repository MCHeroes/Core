package mcheroes.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CoreEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public CoreEvent() {
        this(false);
    }

    public CoreEvent(boolean async) {
        super(async);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public @NotNull HandlerList getHandlers() {
        return getHandlerList();
    }

    public boolean call() {
        Bukkit.getPluginManager().callEvent(this);
        if (this instanceof Cancellable cancellable) {
            return cancellable.isCancelled();
        }

        return false;
    }
}
