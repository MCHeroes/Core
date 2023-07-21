package mcheroes.core.action;

import mcheroes.core.api.action.Action;
import mcheroes.core.api.action.ActionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple class used to communicate data between features to prevent any direct dependency on each other.
 */
public class ActionManager {
    private final Map<Class<? extends Action>, ActionHandler<Action, ?>> handlers = new HashMap<>();

    public <R> R run(Action action) {
        return (R) handlers.get(action.getClass()).handle(action);
    }

    public <T extends Action, R> void register(Class<T> type, ActionHandler<T, R> handler) {
        handlers.put(type, (ActionHandler<Action, ?>) handler);
    }
}
