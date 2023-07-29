package mcheroes.core.minigames;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MinigameManager {
    private String currentMinigame = null;

    @Nullable
    public String getCurrentMinigame() {
        return currentMinigame;
    }

    public void setCurrentMinigame(@NotNull String currentMinigame) {
        this.currentMinigame = currentMinigame;
    }
}
