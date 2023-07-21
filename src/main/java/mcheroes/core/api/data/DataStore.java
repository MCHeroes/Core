package mcheroes.core.api.data;

import mcheroes.core.utils.Position;

import java.io.Closeable;
import java.util.UUID;

public interface DataStore extends Closeable {
    Position getHubSpawn();

    void setHubSpawn(Position hubSpawn);

    int getPlayerPoints(UUID uuid);

    void setPlayerPoints(UUID uuid, int points);

    void load();

    void save();
}
