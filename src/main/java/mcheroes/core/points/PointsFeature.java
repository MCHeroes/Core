package mcheroes.core.points;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.points.actions.PointSetAction;

public class PointsFeature implements CoreFeature, ActionHandler<PointSetAction, Boolean> {
    private final ActionManager actionManager;

    public PointsFeature(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    @Override
    public void load() {
        actionManager.register(PointSetAction.class, this);
    }

    @Override
    public void unload() {

    }

    @Override
    public Boolean handle(PointSetAction action) {


        return true;
    }
}
