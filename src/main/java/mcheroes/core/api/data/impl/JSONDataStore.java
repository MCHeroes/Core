package mcheroes.core.api.data.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import mcheroes.core.api.data.DataStore;
import mcheroes.core.utils.Position;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JSONDataStore implements DataStore {
    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private static final TypeToken<Map<UUID, Integer>> POINTS_TYPE_TOKEN = new TypeToken<>() {
    };

    private final Path path;
    private JsonObject json;

    public JSONDataStore(Path path) {
        this.path = path;
    }

    @Override
    public @NotNull Position getHubSpawn() {
        if (!json.has("hub")) {
            json.add("hub", GSON.toJsonTree(Position.zero("Hub")));
        }

        return GSON.fromJson(json.get("hub"), Position.class);
    }

    @Override
    public void setHubSpawn(@NotNull Position hubSpawn) {
        json.add("hub", GSON.toJsonTree(hubSpawn));
    }

    @Override
    public int getPlayerPoints(@NotNull UUID uuid) {
        if (!json.has("players")) {
            return 0;
        }
        final Map<UUID, Integer> map = GSON.fromJson(json.get("players"), POINTS_TYPE_TOKEN);
        if (map == null || !map.containsKey(uuid)) return 0;

        return map.get(uuid);
    }

    @Override
    public void setPlayerPoints(@NotNull UUID uuid, int points) {
        final Map<UUID, Integer> map = !json.has("players") ? new HashMap<>() : GSON.fromJson(json.get("players"), POINTS_TYPE_TOKEN);
        map.put(uuid, points);

        json.add("players", GSON.toJsonTree(map));
    }

    @Override
    public Set<UUID> getPlayers() {
        if (!json.has("players")) return Collections.emptySet();

        return new HashSet<>(GSON.fromJson(json.get("players"), POINTS_TYPE_TOKEN).keySet());
    }

    @Override
    public void load() {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                Files.writeString(path, "{}");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            this.json = GSON.fromJson(Files.readString(path), JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save() {
        try {
            Files.writeString(path, GSON.toJson(json));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        save();
    }
}
