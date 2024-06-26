package mcheroes.core.api.minigame;

import mcheroes.core.minigames.utils.MinigameIntroducer;
import org.bukkit.entity.Player;

/**
 * This interface will be implemented by a specific class in every gamemode's plugin
 * which will override these methods to handle start/stop actions whenever the core's
 * game manager invokes them.
 */
public interface Minigame {
    /**
     * Returns a unique ID for this minigame that the core can use to identify it.
     * This ID is displayed in the game manager GUI as well.
     *
     * @return the minigame's unique ID
     */
    String getId();

    /**
     * Code that is run when this minigame is started by the game manager.
     */
    void start();

    /**
     * Code that is run when this minigame ends or is stopped by the game manager.
     */
    void stop();

    /**
     * Checks if this minigame has already started, true if so, false otherwise.
     *
     * @return true if this game has already started
     */
    boolean hasStarted();

    /**
     * Checks if this minigame is able to be started currently, true if so, false otherwise.
     *
     * @return true if this game can start
     */
    boolean canStart();

    /**
     * Returns the time limit of the game in seconds.
     * This game will auto-end after this time if not already ended.
     * Use -1 if you want to disable this feature.
     *
     * @return number of seconds the game lasts till it is forced to end, -1 if it has no time limit
     */
    int getMaxSeconds();

    void handlePreGame(Player player);

    MinigameIntroducer getIntroducer();
}
