package mcheroes.core.api.action;

public interface ActionHandler<T extends Action<R>, R> {
    R handle(T action);
}
