package mcheroes.core.minigames;

import org.jetbrains.annotations.Nullable;

public class MinigameManager {
    private String currentMinigame = null;

    @Nullable
    public String getCurrentMinigame() {
        return currentMinigame;
    }

    public void setCurrentMinigame(@Nullable String currentMinigame) {
        this.currentMinigame = currentMinigame;
    }
}
