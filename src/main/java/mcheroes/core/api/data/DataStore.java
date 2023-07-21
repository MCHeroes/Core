package mcheroes.core.api.data;

import mcheroes.core.utils.Position;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.UUID;

public interface DataStore extends Closeable {
    @NotNull Position getHubSpawn();

    void setHubSpawn(@NotNull Position hubSpawn);

    int getPlayerPoints(@NotNull UUID uuid);

    void setPlayerPoints(@NotNull UUID uuid, int points);

    void load();

    void save();
}
