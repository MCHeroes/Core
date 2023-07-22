package mcheroes.core.points.actions.handlers;

import mcheroes.core.api.HashCache;
import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.points.actions.GetPointsAction;

import java.util.UUID;

public class GetPointsActionHandler implements ActionHandler<GetPointsAction, Integer> {
    private final HashCache<UUID, Integer> cache;

    public GetPointsActionHandler(HashCache<UUID, Integer> cache) {
        this.cache = cache;
    }

    @Override
    public Integer handle(GetPointsAction action) {
        return cache.get(action.player());
    }
}
