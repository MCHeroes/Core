package mcheroes.core.api.data;

import mcheroes.core.utils.Position;

import java.io.Closeable;

public interface DataStore extends Closeable {
    Position getHubSpawn();

    void setHubSpawn(Position hubSpawn);

    void load();

    void save();
}
