package mcheroes.core.minigames;

import mcheroes.core.api.minigame.Minigame;

import java.util.HashMap;
import java.util.Map;

public class MinigameRegistry {
    private final Map<String, Minigame> minigames = new HashMap<>();

    public void register(Minigame minigame) {
        minigames.put(minigame.getId(), minigame);
    }

    public Map<String, Minigame> getMinigames() {
        return minigames;
    }

    public Minigame get(String id) {
        return minigames.get(id);
    }
}
