package mcheroes.core.points;

import mcheroes.core.api.CoreEvent;
import org.bukkit.event.Cancellable;

import java.util.UUID;

public class PointsChangeEvent extends CoreEvent implements Cancellable {
    private final UUID player;
    private final int oldPoints;
    private int newPoints;

    private boolean isCancelled;

    public PointsChangeEvent(UUID player, int oldPoints, int newPoints) {
        this.player = player;
        this.oldPoints = oldPoints;
        this.newPoints = newPoints;
    }

    public UUID getPlayer() {
        return player;
    }

    public int getOldPoints() {
        return oldPoints;
    }

    public int getNewPoints() {
        return newPoints;
    }

    public void setNewPoints(int newPoints) {
        this.newPoints = newPoints;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }
}
