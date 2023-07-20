package mcheroes.core.api.data.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mcheroes.core.api.data.DataStore;
import mcheroes.core.utils.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONDataStore implements DataStore {
    private static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    private final Path path;
    private JsonObject json;

    public JSONDataStore(Path path) {
        this.path = path;
    }

    @Override
    public Position getHubSpawn() {
        if (!json.has("hub")) {
            json.add("hub", GSON.toJsonTree(Position.zero("Hub")));
        }

        return GSON.fromJson(json.get("hub"), Position.class);
    }

    @Override
    public void setHubSpawn(Position hubSpawn) {
        json.add("hub", GSON.toJsonTree(hubSpawn));
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
