package mcheroes.core.points.actions.handlers;

import mcheroes.core.api.HashCache;
import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.points.PointsChangeEvent;
import mcheroes.core.points.PointsGUI;
import mcheroes.core.points.actions.SetPointsAction;

import java.util.UUID;

public class SetPointsActionHandler implements ActionHandler<SetPointsAction, Integer> {
    private final HashCache<UUID, Integer> cache;
    private final PointsGUI gui;

    public SetPointsActionHandler(HashCache<UUID, Integer> cache, PointsGUI gui) {
        this.cache = cache;
        this.gui = gui;
    }

    @Override
    public Integer handle(SetPointsAction action) {
        final Integer old = cache.get(action.player());
        final PointsChangeEvent event = new PointsChangeEvent(action.player(), old, action.to());
        if (event.call()) {
            return old;
        }

        cache.put(action.player(), event.getNewPoints());
        gui.updatePoints();

        return old;
    }
}
