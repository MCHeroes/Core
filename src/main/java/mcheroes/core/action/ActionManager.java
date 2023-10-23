package mcheroes.core.action;

import mcheroes.core.api.action.Action;
import mcheroes.core.api.action.ActionHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple class used to communicate data between features to prevent any direct dependency on each other.
 */
public class ActionManager {
    private final Map<Class<? extends Action<?>>, ActionHandler<Action<?>, ?>> handlers = new HashMap<>();

    @Nullable
    public <R> R run(@NotNull Action<R> action) {
        final ActionHandler<Action<?>, ?> handler = handlers.get(action.getClass());
        if (handler == null) return null;

        return (R) handler.handle(action);
    }

    public <T extends Action<R>, R> void set(@NotNull Class<T> type, @NotNull ActionHandler<T, R> handler) {
        handlers.put(type, (ActionHandler<Action<?>, ?>) handler);
    }
}
