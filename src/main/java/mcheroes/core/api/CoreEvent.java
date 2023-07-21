package mcheroes.core.api;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class CoreEvent extends Event {
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

    /**
     * Calls this event using Bukkit methods, then returns true/false if this event is cancellable.
     * <p>
     * If the event is not a Cancellable type event, false will always be returned.
     *
     * @return true if cancelled, false if not cancelled
     */
    public boolean call() {
        Bukkit.getPluginManager().callEvent(this);
        if (this instanceof Cancellable cancellable) {
            return cancellable.isCancelled();
        }

        return false;
    }
}
